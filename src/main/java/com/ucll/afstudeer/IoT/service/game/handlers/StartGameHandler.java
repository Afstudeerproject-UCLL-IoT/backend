package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
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
            return new ServiceActionResponse<>(ServiceError.GAME_DOES_NOT_EXIST);
        }

        // close all other game session if they are still are being played, only 1 game session can be active because we have only 1 room
        // reset all the other devices
        gameRepository.closeAllGameSessionsBeingPlayed();
        notificationService.sendToAll(Event.ENDGAME, "");

        // create a new game session
        var session = new GameSession.Builder()
                .withoutId()
                .withStartTime(LocalDateTime.now())
                .withEndTime(null)
                .build();

        // persist game with session
        var addedGameSession = gameRepository.addGameSession(game, session);

        // get all the devices in the game
        var devices = gameRepository.getAllDevicesInAGame(game);

        // check if the game has device puzzles
        if (devices.isEmpty())
            return new ServiceActionResponse<>(ServiceError.GAME_HAS_NO_PUZZLES);

        // notify that the game has started
        devices.forEach(device ->
                notificationService.send(device, Event.STARTGAME, String.valueOf(addedGameSession.getId())));

        // notify the first device that it's puzzle can be started
        var firstDevice = devices.get(0);
        notificationService.send(firstDevice, Event.STARTPZL, firstDevice.getPuzzle().getName());

        return new ServiceActionResponse<>(addedGameSession);
    }
}
