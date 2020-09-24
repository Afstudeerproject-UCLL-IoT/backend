package core.domain.device;

import core.domain.puzzle.Puzzle;
import core.exceptions.device.InvalidDeviceCreationInputException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Device {
    private final DeviceType type;
    private final Puzzle puzzle;

    private Device(DeviceType type, Puzzle puzzle) {
        this.type = type;
        this.puzzle = puzzle;
    }

    public static Device instance(String input){
        // validate input
        if(input == null || !Pattern.matches("^(ARDUINO)+-[A-Za-z0-9]+$", input)){
            throw new InvalidDeviceCreationInputException();
        }

        // get the data
        var split = input.split("-");
        var type = DeviceType.valueOf(split[0]);
        var puzzleName = split[1];

        // create the device
        return new Device(type, Puzzle.instance(puzzleName, ""));
    }

    public DeviceType getType() {
        return type;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    // overrides
    @Override
    public String toString() {
        return String.format("%s-%s", type.toString(), puzzle.toString());
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Device){
            var other = (Device) o;
            return getPuzzle().equals(other.getPuzzle()) &&  getType().equals(other.getType());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPuzzle(), getType());
    }
}
