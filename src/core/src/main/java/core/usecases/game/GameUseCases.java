package core.usecases.game;

import core.domain.Device;
import core.domain.Game;
import core.domain.Puzzle;

import java.time.LocalDateTime;

public interface GameUseCases {
    boolean createGame(Game game);

    boolean startGame(Game game);

    LocalDateTime endGame(Game game);

    boolean addPuzzleSubscription(Game game,Device subscriber, Puzzle puzzle);
}
