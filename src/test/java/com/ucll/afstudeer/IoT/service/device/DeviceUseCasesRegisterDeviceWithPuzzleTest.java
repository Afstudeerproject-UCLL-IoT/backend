package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.Device;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class DeviceUseCasesRegisterDeviceWithPuzzleTest extends DeviceServiceBase {

    @DisplayName("Test the registration of a device with it's puzzle")
    @ParameterizedTest
    @ValueSource(strings = {"ARDUINO-AwesomePuzzle1", "ARDUINO-AwesomePuzzle2"})
    public void deviceWithPuzzleCanBeRegisteredWithTheRightInput(String input) {
        // stub
        when(deviceRepository.addDeviceWithPuzzle(any(Device.class)))
                .thenReturn(new Device.Builder()
                        .withId(1)
                        .fromDeviceName(input)
                        .build());

        // create device
        var device = new Device.Builder()
                .withoutId()
                .fromDeviceName(input)
                .build();

        // device registration
        device = deviceService.registerDeviceWithPuzzle(device).getValue();
        verify(deviceRepository).addDeviceWithPuzzle(any(Device.class));

        // null assertions
        assertNotNull(device);
        assertNotNull(device.getPuzzle());
        assertNotNull(device.getType());

        // data assertions
        assertEquals(1, device.getId());
        assertEquals(String.format("%d-%s", device.getId(), input), device.toString());
        assertEquals(input.split("-")[1], device.getPuzzle().getName());
    }

    @DisplayName("Registering a device that already exists throws an exception")
    @Test
    public void registeringAnExistingDeviceLogsAConnectionActivity() {
        // TODO
    }
}