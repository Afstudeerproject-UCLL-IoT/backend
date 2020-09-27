package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import core.exceptions.device.DeviceAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DeviceUseCasesRegisterDeviceWithPuzzleTest extends DeviceUseCasesBase {

    @DisplayName("Test the registration of a device with it's puzzle")
    @ParameterizedTest
    @ValueSource(strings = {"ARDUINO-AwesomePuzzle1", "ARDUINO-AwesomePuzzle2"})
    public void deviceWithPuzzleCanBeRegisteredWithTheRightInput(String input){
        // device registration
        var device = deviceUseCases.registerDeviceWithPuzzle(input);
        verify(deviceRepository).addDeviceWithPuzzle(any(Device.class));

        // null assertions
        assertNotNull(device);
        assertNotNull(device.getPuzzle());
        assertNotNull(device.getType());

        // data assertions
        assertEquals(input, device.toString());
        assertEquals(input.split("-")[1], device.getPuzzle().getName());
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