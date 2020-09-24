package core.domain.device;

import core.exceptions.device.InvalidDeviceNameException;

import java.util.regex.Pattern;

public class DeviceName {
    private final String puzzleName;
    private final DeviceRole role;
    private final int number;

    private DeviceName(String puzzleName, DeviceRole role, int number) {
        this.puzzleName = puzzleName;
        this.role = role;
        this.number = number;
    }

    public static DeviceName instance(String deviceName){
        // validate device name with regex and null check
        if(deviceName == null || !Pattern.matches("^.+-(Owner|Subscriber)-[0-9]{1,6}+$", deviceName)){
            throw new InvalidDeviceNameException();
        }

        // split the device name after validation
        var deviceNameSplit = deviceName.split("-");

        // create the needed properties for device name
        var puzzleName = deviceNameSplit[0];
        var deviceRole = DeviceRole.valueOf(deviceNameSplit[1]);
        var deviceNumber = Integer.parseInt(deviceNameSplit[2]);

        // create the correct DeviceName
        if(deviceRole == DeviceRole.Owner){
            return new DeviceName(puzzleName, deviceRole, 1);
        }

        return new DeviceName(puzzleName, deviceRole, deviceNumber);
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public DeviceRole getRole() {
        return role;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s-%s-%d", puzzleName, role, number);
    }
}
