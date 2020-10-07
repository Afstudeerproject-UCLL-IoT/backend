package com.ucll.afstudeer.IoT.dto.out;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;

import java.util.List;

public class GameWithSessionsDto {
    private final Game game;
    private final List<GameSession> gameSessions;

    public GameWithSessionsDto(Game game, List<GameSession> gameSessions) {
        this.game = game;
        this.gameSessions = gameSessions;
    }

    public Game getGame() {
        return game;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }
}
