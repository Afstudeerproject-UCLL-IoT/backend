package core.domain.device;

import core.exceptions.device.InvalidDeviceNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DeviceNameTest {

    @DisplayName("Test device name creation")
    @Test
    public void shouldHaveCorrectDeviceName() {
        // test device owner
        var deviceNameOwner = DeviceName.instance("puzzle1-Owner-999999");

        assertAll("correctDeviceName1",
                () -> assertEquals("puzzle1", deviceNameOwner.getPuzzleName()),
                () -> assertEquals(DeviceRole.Owner, deviceNameOwner.getRole()),
                () -> assertEquals(1, deviceNameOwner.getNumber())
        );

        // test device subscriber
        var deviceNameSubscriber = DeviceName.instance("AnAwesomePuzzleFrom343Technologies-Subscriber-25");

        assertAll("correctDeviceName2",
                () -> assertEquals("AnAwesomePuzzleFrom343Technologies", deviceNameSubscriber.getPuzzleName()),
                () -> assertEquals(DeviceRole.Subscriber, deviceNameSubscriber.getRole()),
                () -> assertEquals(25, deviceNameSubscriber.getNumber())
        );
    }

    @DisplayName("Test device name string conversion")
    @ParameterizedTest
    @ValueSource(strings = {"puzzle1-Owner-1", "AnAwesomePuzzleFrom343Technologies-Subscriber-25"})
    public void deviceNameStringFormatIsCorrect(String deviceNameInput) {
        var deviceName = Device.instance(deviceNameInput);

        assertEquals(deviceNameInput, deviceName.toString());
    }

    @DisplayName("Test if an incorrect device name throws the correct exception")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "Subscriber-2",
            "AwesomePuzzle--1",
            "AwesomePuzzle-MasterMan-1",
            "AwesomePuzzle-Subscriber-",
            "AwesomePuzzle-Owner-",
            "AnAwesomePuzzleFrom343Technologies Subscriber 25",
            "AnAwesomePuzzleFrom343Technologies-Subscriber-1234567",
            "-AnAwesomePuzzleFrom343Technologies-Subscriber-25-"
    })
    public void incorrectDeviceNameThrowsException(String incorrectDeviceName){
        // test cases:
        // no puzzle name provided
        // no role name provided
        // incorrect role name provided
        // no subscriber number provided
        // no owner number provided
        // missing dashes
        // more then 6 numbers
        // too mush dashes

        assertThrows(InvalidDeviceNameException.class, () -> DeviceName.instance(incorrectDeviceName));
    }
}