package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import core.exceptions.subscribe.CannotSubscribeToItselfException;
import core.exceptions.subscribe.DeviceCannotSubscribeToPuzzleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DeviceUseCasesSubscribeToPuzzleTest extends DeviceUseCasesBase {

    @DisplayName("Test if a device can subscribe to a puzzle")
    @Test
    public void aDeviceCanSubscribeToAPuzzle() {
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(true);
        when(puzzleRepository.exists(any(Puzzle.class))).thenReturn(true);

        // parameters
        var deviceName = "ARDUINO-Puzzle1";
        var puzzle = "Puzzle2";

        // subscribe device to puzzle
        var subscription = deviceUseCases.subscribeToPuzzle(deviceName, puzzle);

        // verifies
        verify(deviceRepository).exists(any(Device.class));
        verify(puzzleRepository).exists(any(Puzzle.class));
        verify(puzzleRepository).addSubscription(any(Device.class), any(Puzzle.class));

        // assertions
        assertNotNull(subscription);
        assertNotNull(subscription.getLeft());
        assertNotNull(subscription.getRight());

        assertEquals("ARDUINO-Puzzle1", subscription.getLeft().toString());
        assertEquals("Puzzle2", subscription.getRight().getName());
    }

    @DisplayName("Test that when a device tries to subscribe to it's own puzzleAnExceptionIsThrown")
    @Test
    public void whenADeviceSubscribesToItsOwnPuzzleAnExceptionIsThrown() {
        // parameters
        var deviceName = "ARDUINO-Puzzle1";
        var puzzle = "Puzzle1";

        // check if exception is thrown and that the subscription was not done
        assertThrows(CannotSubscribeToItselfException.class, () -> deviceUseCases.subscribeToPuzzle(deviceName, puzzle));
        verify(deviceRepository, never()).exists(any(Device.class));
        verify(puzzleRepository, never()).exists(any(Puzzle.class));
        verify(puzzleRepository, never()).addSubscription(any(Device.class), any(Puzzle.class));

    }

    @DisplayName("Test that when a device tries to subscribe to an unknown puzzle an exception is thrown")
    @Test
    public void whenADeviceSubscribesToAPuzzleThatDoesNotExistAnExceptionIsThrown() {
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(true);
        when(puzzleRepository.exists(any(Puzzle.class))).thenReturn(false);

        // parameters
        var deviceName = "ARDUINO-RealPuzzle";
        var puzzle = "FakePuzzle";

        // check if an exception is thrown an verify method calls
        assertThrows(DeviceCannotSubscribeToPuzzleException.class, () -> deviceUseCases.subscribeToPuzzle(deviceName, puzzle));
        verify(puzzleRepository).exists(any(Puzzle.class));
        verify(puzzleRepository, never()).addSubscription(any(Device.class), any(Puzzle.class));
    }

    @DisplayName("Test that when an unknown device tries to subscribe to a puzzle an exception is thrown")
    @Test
    public void whenAnUnknownDeviceSubscribesToAPuzzleAnExceptionIsThrown() {
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(false);

        // parameters
        var deviceName = "ARDUINO-FakePuzzle";
        var puzzle = "RealPuzzle";

        // check if an exception is thrown an verify method calls
        assertThrows(DeviceCannotSubscribeToPuzzleException.class, () -> deviceUseCases.subscribeToPuzzle(deviceName, puzzle));
        verify(deviceRepository).exists(any(Device.class));
        verify(puzzleRepository, never()).addSubscription(any(Device.class), any(Puzzle.class));
    }
}
