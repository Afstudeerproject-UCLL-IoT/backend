package core.domain.device;

import core.exceptions.device.InvalidDeviceNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    @Test
    public void creatingADeviceFromADeviceNameGivesAValidDevice(){
        var device = Device.instance("Puzzle1Awesome-Owner-1");

        assertNotNull(device);
        assertNotNull(device.getName());
        assertNotNull(device.getPuzzle());

        assertEquals("Puzzle1Awesome", device.getPuzzle().getName());
    }

    @DisplayName("Test when the device name is not valid, the right exception is thrown, this is already tested but now test if it's wrapped correctly")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Puzzle1", "Owner", "Puzzle1---", "Puzzle-Owner-"})
    public void creatingADeviceFromAWrongDeviceNameGivesAnException(String deviceNameInput){
        assertThrows(InvalidDeviceNameException.class, () -> Device.instance(deviceNameInput));
    }
}