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
                                                        PuzzleRepository puzzleRepository,
                                                        GameRepository gameRepository,
                                                        NotificationService notificationService) {

        // null check
        if (puzzle == null)
            throw new IllegalArgumentException("Puzzle cannot be null");

        // check if the game session exists and is in use
        if (!gameRepository.gameSessionExistsAndIsBeingPlayed(gameSessionId))
            return new ServiceActionResponse<>(ServiceError.NO_GAME_SESSION_WAS_ACTIVE);

        // send attempt to feedback device
        notificationService.sendToFeedback(String.format("%s_Solved_%b", puzzle.getName(), true));

        // find the devices that are subscribed to this puzzle
        var devices = puzzleRepository.getSubscriptions(puzzle);

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
