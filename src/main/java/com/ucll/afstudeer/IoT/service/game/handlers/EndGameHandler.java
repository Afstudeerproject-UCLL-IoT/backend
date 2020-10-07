package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;

public class EndGameHandler {

    public static ServiceActionResponse<GameSession> handle(Game game, LocalDateTime endTime, GameRepository gameRepository){
        // null checks
        if(game == null || endTime == null)
            throw new IllegalArgumentException("For ending a game the game and end time cannot be null");

        // TODO check if end time is not before start time

        var session = gameRepository.updateLastGameSessionEndTimeInAGame(game, endTime);
        return new ServiceActionResponse<>(session);
    }
}
