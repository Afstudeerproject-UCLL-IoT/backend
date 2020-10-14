package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameProgress;
import com.ucll.afstudeer.IoT.domain.PuzzleProgress;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class GetGameProgressHandler {

    public static ServiceActionResponse<GameProgress> handle(Game game, GameRepository gameRepository) {
        // null check
        if (game == null)
            throw new IllegalArgumentException("Game cannot be null for retrieving the progress");

        // get the current game session
        var gameSession = gameRepository.getCurrentlyPlayedGameSession(game);

        // check if the game session was found
        if (gameSession == null) {
            return new ServiceActionResponse<>(null);
        }

        // retrieve the progress and return it
        var puzzleProgresses = gameRepository.getAllPuzzleProgressesForAGameWithGameSession(game, gameSession);

        // set the start time of the first puzzle to the start time of the game
        if (!puzzleProgresses.isEmpty()) {
            var firstPuzzleProgress = puzzleProgresses.get(0);
            puzzleProgresses.set(0, new PuzzleProgress.Builder()
                    .withStartTime(gameSession.getStart())
                    .withEndTime(firstPuzzleProgress.getEndTime())
                    .withPuzzleName(firstPuzzleProgress.getPuzzleName())
                    .withAmountOfAttempts(firstPuzzleProgress.getTotalAttempts())
                    .build());
        }

        // build game progress
        var gameProgress = new GameProgress.Builder()
                .withGameName(game.getName())
                .withPuzzleProgresses(puzzleProgresses)
                .build();

        // return game progress
        return new ServiceActionResponse<>(gameProgress);
    }
}
