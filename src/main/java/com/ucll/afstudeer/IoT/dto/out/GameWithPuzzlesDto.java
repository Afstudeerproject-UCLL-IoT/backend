package com.ucll.afstudeer.IoT.dto.out;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;

import java.util.List;

public class GameWithPuzzlesDto {
    private final Game game;
    private final List<Device> devicePuzzles;

    public GameWithPuzzlesDto(Game game, List<Device> devicePuzzles) {
        this.game = game;
        this.devicePuzzles = devicePuzzles;
    }

    public Game getGame() {
        return game;
    }

    public List<Device> getDevicePuzzles() {
        return devicePuzzles;
    }
}
