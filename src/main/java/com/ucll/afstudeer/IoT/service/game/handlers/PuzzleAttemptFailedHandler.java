package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.domain.PuzzleAttempt;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

import java.time.LocalDateTime;

public class PuzzleAttemptFailedHandler {

    public static ServiceActionResponse<Boolean> handle(Puzzle puzzle,
                                                        LocalDateTime at,
                                                        int gameSessionId,
                                                        GameRepository gameRepository,
                                                        NotificationService notificationService) {
        // null check
        if (puzzle == null)
            throw new IllegalArgumentException("Puzzle cannot be null");

        // check if the game session exists and is in use
        if (gameRepository.gameSessionExistsAndIsBeingPlayed(gameSessionId))
            return new ServiceActionResponse<>(ServiceError.NO_GAME_SESSION_WAS_ACTIVE);

        // send attempt to feedback device
        notificationService.sendToFeedback(String.format("%s_Solved_%b", puzzle.getName(), false));

        // create the attempt and add it
        var attempt = new PuzzleAttempt.Builder()
                .withoutId()
                .withAttemptAt(at)
                .withSuccess(false)
                .withPuzzleName(puzzle.getName())
                .withGameSessionId(gameSessionId)
                .build();

        // add the puzzle attempt
        gameRepository.addPuzzleAttempt(attempt);
        return ServiceActionResponse.Success();
    }
}
