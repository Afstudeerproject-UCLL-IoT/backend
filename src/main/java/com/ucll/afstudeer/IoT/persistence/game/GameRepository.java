package com.ucll.afstudeer.IoT.persistence.game;


import com.ucll.afstudeer.IoT.domain.*;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends GenericRepository<Game, String> {

    /**
     * Insert a new game into the data store
     * @param game The game
     * @return The created game
     */
    Game add(Game game);

    /**
     * Insert a new game session into the data store
     * @param game The game
     * @param session The session for the game
     * @return The created game session
     */
    GameSession addGameSession(Game game, GameSession session);

    /**
     * Update the end time of a game session in the data store
     * @param game The game
     * @param endTime The end time of the game session
     * @return The updated game session or null if no game session was updated
     */
    GameSession updateLastGameSessionEndTimeInAGame(Game game, LocalDateTime endTime);

    /**
     * Check if all entities exist in the data store so that a puzzle subscription is possible
     * @param subscriber The device that is going to subscribe to a puzzle
     * @param puzzle The puzzle that is being subscribed to
     * @param game The game the subscription is intended for
     * @return True if all entities exist, false otherwise
     */
    boolean gamePuzzleSubscriptionIsPossible(Device subscriber, Puzzle puzzle, Game game);

    /**
     * Insert a new game puzzle subscription into the data store
     * @param subscriber The device that is going to subscribe to a puzzle
     * @param puzzle The puzzle that is being subscribed to
     * @param game The game the subscription is intended for
     * @param position The position of the device in the game, 1 (first device in the game), 2 (second device in game), ...
     */
    void addGamePuzzleSubscription(Device subscriber, Puzzle puzzle, Game game, int position);

    /**
     * Get all the games from the data store
     * @return A list that contains all the games or an empty list when nothing was found
     */
    List<Game> getAllGames();

    /**
     * Get all the devices in a game ordered by it's position ascending
     * @param game The game
     * @return A list of all the devices in the game ordered by the position or an empty list
     */
    List<Device> getAllDevicesInAGame(Game game);

    /**
     * Get all the game sessions for a game
     * @param game The game
     * @return A list containing the game sessions or an empty list when no game session were found
     */
    List<GameSession> getAllGameSessions(Game game);

    /**
     * Insert a new puzzle attempt into the data store
     * @param puzzleAttempt The puzzle attempt
     */
    void addPuzzleAttempt(PuzzleAttempt puzzleAttempt);

    /**
     * Check if the game session exists in the data store and is currently in use for a game
     * @param gameSessionId The identifier of the game session
     * @return A boolean indicating the result of the query
     */
    boolean gameSessionExistsAndIsBeingPlayed(int gameSessionId);

    GameSession getCurrentlyPlayedGameSession(Game game);
}
