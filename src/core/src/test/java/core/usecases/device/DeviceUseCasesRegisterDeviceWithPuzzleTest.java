package core.usecases.device;

import core.domain.device.Device;
import core.domain.puzzle.Puzzle;
import core.exceptions.device.DeviceAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DeviceUseCasesRegisterDeviceWithPuzzleTest extends DeviceUseCasesBase {

    @DisplayName("Test device and it's puzzle registration")
    @ParameterizedTest
    @ValueSource(strings = {"ARDUINO-AwesomePuzzle1", "ARDUINO-AwesomePuzzle2"})
    public void validDeviceWithPuzzleCanBeRegistered(String deviceName){
        // device registration
        var device = deviceUseCases.registerDeviceWithPuzzle(deviceName);
        verify(deviceRepository).add(any(Device.class));
        verify(puzzleRepository).add(any(Puzzle.class));

        // null assertions
        assertNotNull(device);
        assertNotNull(device.getPuzzle());
        assertNotNull(device.getType());

        // data assertions
        assertEquals(deviceName, device.toString());
        assertEquals(deviceName.split("-")[1], device.getPuzzle().getName());
    }

    @DisplayName("Registering a device that already exists throws an exception")
    @Test
    public void registeringAnExistingDeviceThrowsAnException(){
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(true);

        // check if an exception is thrown
        assertThrows(DeviceAlreadyExistsException.class, () -> deviceUseCases.registerDeviceWithPuzzle("ARDUINO-AwesomePuzzle1"));
        verify(deviceRepository).exists(any(Device.class));
    }
}