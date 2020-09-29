package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import core.exceptions.subscribe.CannotSubscribeToItselfException;
import core.exceptions.subscribe.DeviceCannotSubscribeToPuzzleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
public class DeviceUseCasesSubscribeToPuzzleTest extends DeviceUseCasesBase {

    @DisplayName("Test if a device can subscribe to a puzzle")
    @Test
    public void aDeviceCanSubscribeToAPuzzle() {
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(true);
        when(puzzleRepository.exists(any(Puzzle.class))).thenReturn(true);

        // create subscriber
        var subscriber = new Device.Builder()
                .withoutId()
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        // create puzzle
        var puzzle = new Puzzle.Builder()
                .withName("Puzzle2")
                .withoutSolution()
                .build();

        // subscribe device to puzzle
        var subscription = deviceUseCases.subscribeToPuzzle(subscriber, puzzle);

        // verifies
        verify(deviceRepository).exists(any(Device.class));
        verify(puzzleRepository).exists(any(Puzzle.class));
        verify(puzzleRepository).addSubscription(any(Device.class), any(Puzzle.class));

        // assertions
        assertTrue(subscription);
    }

    @DisplayName("Test that when a device tries to subscribe to it's own puzzleAnExceptionIsThrown")
    @Test
    public void whenADeviceSubscribesToItsOwnPuzzleAnExceptionIsThrown() {
        // create subscriber
        var subscriber = new Device.Builder()
                .withoutId()
                .fromDeviceName("ARDUINO-Puzzle1")
                .build();

        // create puzzle
        var puzzle = new Puzzle.Builder()
                .withName("Puzzle1")
                .withoutSolution()
                .build();

        // check if exception is thrown and that the subscription was not done
        assertThrows(CannotSubscribeToItselfException.class, () -> deviceUseCases.subscribeToPuzzle(subscriber, puzzle));

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

        // create subscriber
        var subscriber = new Device.Builder()
                .withoutId()
                .fromDeviceName("ARDUINO-RealPuzzle")
                .build();

        // create puzzle
        var puzzle = new Puzzle.Builder()
                .withName("FakePuzzle")
                .withoutSolution()
                .build();

        // check if an exception is thrown an verify method calls
        assertThrows(DeviceCannotSubscribeToPuzzleException.class, () -> deviceUseCases.subscribeToPuzzle(subscriber, puzzle));
        verify(puzzleRepository).exists(any(Puzzle.class));
        verify(puzzleRepository, never()).addSubscription(any(Device.class), any(Puzzle.class));
    }

    @DisplayName("Test that when an unknown device tries to subscribe to a puzzle an exception is thrown")
    @Test
    public void whenAnUnknownDeviceSubscribesToAPuzzleAnExceptionIsThrown() {
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(false);

        // create subscriber
        var subscriber = new Device.Builder()
                .withoutId()
                .fromDeviceName("ARDUINO-FakePuzzle")
                .build();

        // create puzzle
        var puzzle = new Puzzle.Builder()
                .withName("RealPuzzle")
                .withoutSolution()
                .build();

        // check if an exception is thrown an verify method calls
        assertThrows(DeviceCannotSubscribeToPuzzleException.class, () -> deviceUseCases.subscribeToPuzzle(subscriber, puzzle));

        verify(deviceRepository).exists(any(Device.class));
        verify(puzzleRepository, never()).addSubscription(any(Device.class), any(Puzzle.class));
    }
}
*/
