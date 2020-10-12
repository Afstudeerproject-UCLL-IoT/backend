package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.domain.PuzzleAttempt;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

import java.time.LocalDateTime;

public class PuzzleAttemptSuccessfulHandler {

    public static ServiceActionResponse<Boolean> handle(Puzzle puzzle,
                                                        LocalDateTime at,
                                                        int gameSessionId,
                                                        DeviceRepository deviceRepository,
                                                        PuzzleRepository puzzleRepository,
                                                        GameRepository gameRepository,
                                                        NotificationService notificationService) {

        // null check
        if (puzzle == null)
            throw new IllegalArgumentException("Puzzle cannot be null");

        // check if the game session exists and is in use
        if (gameRepository.gameSessionExistsAndIsBeingPlayed(gameSessionId))
            return new ServiceActionResponse<>(ServiceError.NO_GAME_SESSION_WAS_ACTIVE);

        // send attempt to feedback device
        notificationService.sendToFeedback(String.format("%s_Solved_%b", puzzle.getName(), true));

        // find the devices that are subscribed to this puzzle
        // puzzle can have many subscribers but currently it's only 1
        var devices = puzzleRepository.getSubscriptions(puzzle);

        // TODO if more devices subscribe to a puzzle check all their online statuses
        // check if this device is online, if not skip the puzzle
        if(!devices.isEmpty() && !deviceRepository.getOnlineStatus(devices.get(0))){
            var device = devices.get(0);
            return PuzzleAttemptSuccessfulHandler.handle(device.getPuzzle(), at, gameSessionId, deviceRepository, puzzleRepository, gameRepository, notificationService);
        }

        // start the next puzzle
        devices.forEach(device ->
                notificationService.send(device, Event.STARTPZL, device.getPuzzle().getName()));


        // create the attempt
        var attempt = new PuzzleAttempt.Builder()
                .withoutId()
                .withAttemptAt(at)
                .withSuccess(true)
                .withPuzzleName(puzzle.getName())
                .withGameSessionId(gameSessionId)
                .build();

        // add the puzzle attempt
        gameRepository.addPuzzleAttempt(attempt);
        return ServiceActionResponse.Success();
    }
}
