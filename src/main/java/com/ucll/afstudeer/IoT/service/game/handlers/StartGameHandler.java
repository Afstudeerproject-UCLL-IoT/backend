package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

import java.time.LocalDateTime;

public class StartGameHandler {

    public static ServiceActionResponse<GameSession> handle(Game game, GameRepository gameRepository, NotificationService notificationService) {
        // null check
        if (game == null)
            throw new IllegalArgumentException("Game cannot be null");

        // check if the game that is going to start exists
        if (!gameRepository.exists(game.getName())) {
            return new ServiceActionResponse<>("The game that is going to start does not exist");
        }

        // create a new game session
        var session = new GameSession.Builder()
                .withoutId()
                .withStartTime(LocalDateTime.now())
                .withEndTime(null)
                .build();

        // persist game with session
        var addedGameSession = gameRepository.addGameSession(game, session);

        // get the first device with puzzle in the game
        var device = gameRepository.getDeviceInGameByPosition(game, 1);

        // notify the device that the game has started
        notificationService.send(device, Event.STARTPZL, device.getPuzzle().getName());

        return new ServiceActionResponse<>(addedGameSession);
    }
}
