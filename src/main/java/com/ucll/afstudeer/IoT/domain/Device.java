package com.ucll.afstudeer.IoT.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class Device {
    private final int id;
    private final DeviceType type;
    private final Puzzle puzzle;

    private Device(int id, DeviceType type, Puzzle puzzle) {
        this.id = id;
        this.type = type;
        this.puzzle = puzzle;
    }

    public int getId() {
        return id;
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
        if (id == 0) {
            return String.format("%s-%s", type.toString(), puzzle.toString());
        }

        return String.format("%d-%s-%s", id, type.toString(), puzzle.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Device) {
            var other = (Device) o;
            return Objects.equals(other.getPuzzle(), getPuzzle()) &&
                    Objects.equals(other.getType(), getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPuzzle(), getType());
    }

    // builder
    public static class Builder {
        private int id;
        private DeviceType type;
        private Puzzle puzzle;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withoutId() {
            this.id = 0;
            return this;
        }

        public Builder withPuzzle(Puzzle puzzle) {
            this.puzzle = puzzle;
            return this;
        }

        public Builder withoutPuzzle() {
            this.puzzle = null;
            return this;
        }

        public Builder withDeviceType(DeviceType type) {
            this.type = type;
            return this;
        }

        public Builder withoutDeviceType() {
            this.type = DeviceType.NONE;
            return this;
        }

        public Builder fromDeviceName(String deviceName) {
            // validate input
            if (deviceName == null || !Pattern.matches("^(ARDUINO)+-[A-Za-z0-9]+$", deviceName)) {
                throw new IllegalArgumentException("Device name is not correct");
            }

            // assign data
            var split = deviceName.split("-");
            type = DeviceType.valueOf(split[0]);
            puzzle = new Puzzle.Builder()
                    .withName(split[1])
                    .withoutSolution()
                    .build();

            return this;
        }

        public Device build() {
            return new Device(id, type, puzzle);
        }
    }
}
