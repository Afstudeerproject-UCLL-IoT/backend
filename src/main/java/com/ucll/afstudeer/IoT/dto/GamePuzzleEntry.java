package com.ucll.afstudeer.IoT.dto;

public class GamePuzzleEntry {
    private final int id;
    private final String deviceType;
    private final String puzzleName;
    private final String puzzleSolution;
    private final int position;

    public GamePuzzleEntry(int id, String deviceType, String puzzleName, String puzzleSolution, int position) {
        this.id = id;
        this.deviceType = deviceType;
        this.puzzleName = puzzleName;
        this.puzzleSolution = puzzleSolution;
        this.position = position;
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

    public int getPosition() {
        return position;
    }
}
