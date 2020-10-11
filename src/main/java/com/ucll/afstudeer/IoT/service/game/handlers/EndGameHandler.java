package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

import java.time.LocalDateTime;

public class EndGameHandler {

    public static ServiceActionResponse<GameSession> handle(Game game,
                                                            LocalDateTime endTime,
                                                            GameRepository gameRepository,
                                                            NotificationService notificationService) {
        // null checks
        if (game == null || endTime == null)
            throw new IllegalArgumentException("For ending a game the game and end time cannot be null");

        // check if the game exists
        if (!gameRepository.exists(game.getName())) {
            return new ServiceActionResponse<>(ServiceError.GAME_DOES_NOT_EXIST);
        }

        // update game end time
        var session = gameRepository.updateLastGameSessionEndTimeInAGame(game, endTime);

        // check if game session was updated
        if (session == null)
            return new ServiceActionResponse<>(ServiceError.NO_GAME_SESSION_WAS_ACTIVE);

        // get all devices in the game
        var devices = gameRepository.getAllDevicesInAGame(game);

        // sent game ended event to all the devices in that game
        devices.forEach(device ->
                notificationService.send(device, Event.ENDGAME, ""));

        // send game ended event to feedback device
        notificationService.sendToFeedback(Event.ENDGAME.toString());
        return new ServiceActionResponse<>(session);
    }
}
