package com.ucll.afstudeer.IoT.dto;

public class DevicePuzzleDto {
    private final int id;
    private final String deviceType;
    private final String puzzleName;
    private final String puzzleSolution;

    public DevicePuzzleDto(int id, String deviceType, String puzzleName, String puzzleSolution) {
        this.id = id;
        this.deviceType = deviceType;
        this.puzzleName = puzzleName;
        this.puzzleSolution = puzzleSolution;
    }

    public int getId() {
        return id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public String getPuzzleSolution() {
        return puzzleSolution;
    }
}
