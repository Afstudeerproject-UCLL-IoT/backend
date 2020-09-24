package core.domain.device;

import core.domain.puzzle.Puzzle;
import core.exceptions.device.InvalidDeviceNameException;

public class Device {
    private final DeviceName name;
    private final Puzzle puzzle;

    private Device(DeviceName name, Puzzle puzzle) {
        this.name = name;
        this.puzzle = puzzle;
    }

    public static Device instance(String deviceNameInput){
        // null check
        if(deviceNameInput == null){
            throw new InvalidDeviceNameException();
        }

        // create params and return
        var deviceName = DeviceName.instance(deviceNameInput);
        var puzzle = Puzzle.instance(deviceName.getPuzzleName());

        return new Device(deviceName, puzzle);
    }

    public DeviceName getName() {
        return name;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    @Override
    public String toString(){
        return name.toString();
    }
}
