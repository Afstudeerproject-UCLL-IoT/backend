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

        // get all the devices in the game
        var devices = gameRepository.getAllDevicesInAGame(game);

        // notify all devices that the game has started
        devices.forEach(device -> notificationService.send(device, Event.STARTGAME, String.valueOf(addedGameSession.getId())));

        // notify the first device that the game has started
        var firstDevice = devices.get(0);
        notificationService.send(firstDevice, Event.STARTPZL, firstDevice.getPuzzle().getName());

        return new ServiceActionResponse<>(addedGameSession);
    }
}
