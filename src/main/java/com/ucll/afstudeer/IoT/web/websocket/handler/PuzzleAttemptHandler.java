package com.ucll.afstudeer.IoT.web.websocket.handler;

import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.service.game.GameService;

import java.time.LocalDateTime;

public class PuzzleAttemptHandler {

    public static void handle(LocalDateTime puzzleAttemptAt,
                              String puzzleName,
                              int gameSessionId,
                              boolean isSolved,
                              GameService gameService) {

        // create the puzzle
        var puzzle = new Puzzle.Builder()
                .withName(puzzleName)
                .withoutSolution()
                .build();

        // service call
        if (isSolved) {
            gameService.puzzleAttemptSuccessful(puzzle, gameSessionId, puzzleAttemptAt);
        } else {
            gameService.puzzleAttemptFailed(puzzle, gameSessionId, puzzleAttemptAt);
        }
    }
}
