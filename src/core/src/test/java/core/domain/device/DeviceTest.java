package core.domain.device;

import core.domain.Device;
import core.domain.DeviceType;
import core.exceptions.device.InvalidDeviceCreationInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    @DisplayName("Test if given the right input a device with a puzzle is created")
    @Test
    public void creatingAValidDeviceWithPuzzleSucceeds(){
        var device = Device.instance("ARDUINO-AwesomePuzzle1");

        assertNotNull(device);
        assertNotNull(device.getType());
        assertNotNull(device.getPuzzle());

        assertEquals(DeviceType.ARDUINO, device.getType());
        assertEquals("AwesomePuzzle1", device.getPuzzle().getName());
    }

    @DisplayName("Test the toString method call")
    @Test
    public void testToStringMethod(){
        var device = Device.instance("ARDUINO-AwesomePuzzle1");

        assertEquals("ARDUINO-AwesomePuzzle1", device.toString());
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
    public void creatingADeviceWithInvalidInputThrowsAnException(String deviceNameInput){
        assertThrows(InvalidDeviceCreationInputException.class, () -> Device.instance(deviceNameInput));
    }
}