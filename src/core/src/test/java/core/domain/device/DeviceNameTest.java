package core.domain.device;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceNameTest {

    @DisplayName("Test device name creation")
    @Test()
    public void shouldHaveCorrectDeviceName() {
        // test first device owner
        var deviceNameOwner = DeviceName.For("puzzle1-Owner-999999");

        assertAll("correctDeviceName1",
                () -> assertEquals("puzzle1", deviceNameOwner.getPuzzleName()),
                () -> assertEquals(DeviceRole.Owner, deviceNameOwner.getRole()),
                () -> assertEquals(1, deviceNameOwner.getNumber())
        );

        // test device subscriber
        var deviceNameSubscriber = DeviceName.For("AnAwesomePuzzleFrom343Technologies-Subscriber-25");

        assertAll("correctDeviceName2",
                () -> assertEquals("AnAwesomePuzzleFrom343Technologies", deviceNameSubscriber.getPuzzleName()),
                () -> assertEquals(DeviceRole.Subscriber, deviceNameSubscriber.getRole()),
                () -> assertEquals(25, deviceNameSubscriber.getNumber())
        );
    }

    @DisplayName("Test device name string conversion")
    @Test()
    public void deviceNameStringFormatIsCorrect() {
        var deviceNameOwner = DeviceName.For("puzzle1-Owner-1");
        var deviceNameSubscriber = DeviceName.For("AnAwesomePuzzleFrom343Technologies-Subscriber-25");

        assertAll("Correct string conversion",
                () -> assertEquals("puzzle1-Owner", deviceNameOwner.toString()),
                () -> assertEquals("AnAwesomePuzzleFrom343Technologies-Subscriber-25", deviceNameSubscriber.toString())
        );
    }

    @DisplayName("Test if an incorrect device name throws the correct exception")
    @Test
    // TODO move each case to separate private method
    public void incorrectDeviceNameThrowsException(){
        // null given as parameter
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For(null);
        });

        // no data provided
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("");
        });

        // no puzzle name provided
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("Subscriber-2");
        });

        // no role name provided
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("AwesomePuzzle--1");
        });

        // incorrect role name provided
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("AwesomePuzzle-MasterMan-1");
        });

        // no subscriber number provided
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("AwesomePuzzle-Subscriber-");
        });

        // no owner number provided
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("AwesomePuzzle-Owner-");
        });

        // missing dashes
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("AnAwesomePuzzleFrom343Technologies Subscriber 25");
        });

        // more then 6 numbers
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("AnAwesomePuzzleFrom343Technologies-Subscriber-1234567");
        });

        // too mush dashes
        assertThrows(InvalidDeviceNameException.class, () ->{
            DeviceName.For("-AnAwesomePuzzleFrom343Technologies-Subscriber-25-");
        });
    }
}