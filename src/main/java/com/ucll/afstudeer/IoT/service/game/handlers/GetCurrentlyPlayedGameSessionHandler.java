package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class GetCurrentlyPlayedGameSessionHandler {

    public static ServiceActionResponse<GameSession> handle(Game game, GameRepository gameRepository) {
        // get the game session
        var gameSession = gameRepository.getCurrentlyPlayedGameSession(game);

        return new ServiceActionResponse<>(gameSession);
    }
}
