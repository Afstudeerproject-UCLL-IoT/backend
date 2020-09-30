package com.ucll.afstudeer.IoT.persistence.game;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.util.List;

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
    void addGamePuzzleSubscription(Device subscriber, Puzzle puzzle, Game game, int position);

    // check if a device with it's puzzle can become the first puzzle in a game
    boolean firstDevicePuzzleIsPossible(Device device, Game game);

    List<Game> getAllGames();

    // get the devices in the game, the list order is also the order the puzzles need to be solved
    List<Device> getAllDevicesInAGame(Game game);

}
