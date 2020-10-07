package com.ucll.afstudeer.IoT.persistence.game;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends GenericRepository<Game, String> {

    // persist a new game and return the persisted entity with the id set
    Game add(Game game);

    // persist a new game session
    GameSession addGameSession(Game game, GameSession session);

    GameSession updateLastGameSessionEndTimeInAGame(Game game, LocalDateTime endTime);

    // get the device in a game by it's position
    Device getDeviceInGameByPosition(Game game, int position);

    // check if a device can subscribe to another puzzle for a game
    boolean gamePuzzleSubscriptionIsPossible(Device subscriber, Puzzle puzzle, Game game);

    // subscribe the device to the puzzle for the game
    void addGamePuzzleSubscription(Device subscriber, Puzzle puzzle, Game game, int position);

    List<Game> getAllGames();

    // get the devices in the game, the list order is also the order off the puzzles
    List<Device> getAllDevicesInAGame(Game game);

    List<GameSession> getAllGameSessions(Game game);

}
