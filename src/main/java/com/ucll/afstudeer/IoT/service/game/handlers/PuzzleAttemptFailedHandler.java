package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.domain.PuzzleAttempt;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
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

        gameRepository.addPuzzleAttempt(attempt);

        return ServiceActionResponse.Success();
    }
}
