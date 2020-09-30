package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.exception.game.GameDoesNotExistException;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

import java.time.LocalDateTime;

public class StartGameHandler {

    public static boolean handle(Game game, GameRepository gameRepository, NotificationService notificationService){
        // null check
        if(game == null)
            throw new IllegalArgumentException("Game cannot be null");

        // check if the game that is going to start exists
        if(!gameRepository.isPresent(game)){
            throw new GameDoesNotExistException();
        }

        // create a new game session
        var session = new GameSession.Builder()
                .withoutId()
                .withStartTime(LocalDateTime.now())
                .withEndTime(null)
                .build();

        // persist game with session
        gameRepository.addGameSession(game, session);

        // get the first device with puzzle in the game
        var device = gameRepository.getFirstDevicePuzzle(game);

        // notify the device that the game has started
        notificationService.send(device, Event.GAME_STARTED);

        return true;
    }
}
