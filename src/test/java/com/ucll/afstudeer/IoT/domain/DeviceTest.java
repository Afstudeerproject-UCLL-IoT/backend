package com.ucll.afstudeer.IoT.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    @DisplayName("Test if given the right input a device with a puzzle is created")
    @Test
    public void creatingAValidDeviceWithPuzzleSucceeds() {
        var device = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-AwesomePuzzle1")
                .build();

        assertNotNull(device);
        assertNotNull(device.getType());
        assertNotNull(device.getPuzzle());

        assertEquals(DeviceType.ARDUINO, device.getType());
        assertEquals("AwesomePuzzle1", device.getPuzzle().getName());
        assertEquals(1, device.getId());
    }

    @DisplayName("Test the toString method call")
    @Test
    public void testToStringMethod() {
        var device = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-AwesomePuzzle1")
                .build();

        assertEquals("1-ARDUINO-AwesomePuzzle1", device.toString());

        var deviceWithoutId = new Device.Builder()
                .withId(1)
                .fromDeviceName("ARDUINO-AwesomePuzzle1")
                .build();

        assertEquals("1-ARDUINO-AwesomePuzzle1", deviceWithoutId.toString());
    }

    @DisplayName("Invalid input for the creation of a device throws an exception")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "bla-bla",
            "arduino-puzzle1",
            "bla-puzzle1",
            "-ARDUINO-puzzle1",
            "AARDUINO-puzzle1"
    })
    public void creatingADeviceWithInvalidInputThrowsAnException(String deviceNameInput) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Device.Builder()
                    .withoutId()
                    .fromDeviceName(deviceNameInput)
                    .build();
        });
    }
}