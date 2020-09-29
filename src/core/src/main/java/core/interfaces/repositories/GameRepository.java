package core.interfaces.repositories;

import core.domain.Device;
import core.domain.Game;
import core.domain.GameSession;
import core.domain.Puzzle;

public interface GameRepository extends GenericRepository<Game> {

    // persist a new game
    void add(Game game);

    // persist a new game session
    void addGameSession(Game game, GameSession session);

    // get the first puzzle of the game
    Device getFirstDevicePuzzle(Game game);

    // check if a device can subscribe to another puzzle for a game
    boolean GamePuzzleSubscriptionIsPossible(Device subscriber, Puzzle puzzle, Game game);

    // subscribe the device to the puzzle for the game
    void addGamePuzzleSubscription(Device subscriber, Puzzle puzzle, Game game);
}
