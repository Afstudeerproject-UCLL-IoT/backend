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
        // create device
        var device = new Device.Builder()
                .withoutId()
                .fromDeviceName(input)
                .build();

        // device registration
        var success = deviceUseCases.registerDeviceWithPuzzle(device);
        verify(deviceRepository).addDeviceWithPuzzle(any(Device.class));

        // null assertions
        assertNotNull(device);
        assertNotNull(device.getPuzzle());
        assertNotNull(device.getType());

        // data assertions
        assertEquals(input, device.toString());
        assertEquals(input.split("-")[1], device.getPuzzle().getName());
        assertTrue(success);
    }

    @DisplayName("Registering a device that already exists throws an exception")
    @Test
    public void registeringAnExistingDeviceThrowsAnException(){
        // stub
        when(deviceRepository.exists(any(Device.class))).thenReturn(true);

        // create device
        var device = new Device.Builder()
                .withoutId()
                .fromDeviceName("ARDUINO-AwesomePuzzle1")
                .build();

        // check if an exception is thrown
        assertThrows(DeviceAlreadyExistsException.class, () -> deviceUseCases.registerDeviceWithPuzzle(device));
        verify(deviceRepository).exists(any(Device.class));
    }
}