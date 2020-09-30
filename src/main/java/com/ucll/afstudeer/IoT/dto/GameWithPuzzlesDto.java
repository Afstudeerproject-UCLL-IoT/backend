package com.ucll.afstudeer.IoT.dto;

import java.util.List;

public class GameWithPuzzlesDto {
    private final String gameName;
    private final List<GamePuzzleEntry> devicePuzzles;

    public GameWithPuzzlesDto(String gameName, List<GamePuzzleEntry> devicePuzzles) {
        this.gameName = gameName;
        this.devicePuzzles = devicePuzzles;
    }

    public String getGameName() {
        return gameName;
    }

    public List<GamePuzzleEntry> getDevicePuzzles() {
        return devicePuzzles;
    }
}
