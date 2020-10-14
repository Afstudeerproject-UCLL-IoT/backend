package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.*;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.dto.out.GameWithSessionsDto;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface GameService {
    // ACTIONS

    /**
     * Create a new game in the system
     *
     * @param game The game
     * @return The created game or the existing game if it was found
     */
    ServiceActionResponse<Game> createGame(Game game);

    /**
     * Start a game with it's puzzles and notify the devices of this event. The first puzzle device will receive a start puzzle event.
     *
     * @param game The game
     * @return The game session that has been started or a service error indicating that the game does not exist or that their are no puzzles for the game
     */
    ServiceActionResponse<GameSession> startGame(Game game);

    /**
     * End a game that is currently being played, also notify each device of this event
     *
     * @param game    The game
     * @param endTime The end time of the game
     * @return The ended game session or a service error indicating the game does not exist or no game was being played
     */
    ServiceActionResponse<GameSession> endGame(Game game, LocalDateTime endTime);

    /**
     * Add a new puzzle subscription for a game in the system.
     *
     * @param game         The game
     * @param subscription The puzzle subscription
     * @return A boolean indicating if the operation was successful or a service error indicating the subscription was not valid?
     * This happens when the puzzle subscribes to itself or when not all entities in the subscription are known to the system
     */
    ServiceActionResponse<Boolean> addPuzzleSubscription(Game game, PuzzleSubscription subscription);

    /**
     * Add multiple puzzle subscriptions for a game in the system
     *
     * @param game          The game
     * @param subscriptions A list containing the puzzle subscriptions
     * @return A boolean indicating if this operation was successful or not
     */
    ServiceActionResponse<Boolean> addPuzzleSubscriptions(Game game, List<PuzzleSubscription> subscriptions);

    /**
     * Do a successful puzzle attempt for a game session being played
     *
     * @param puzzle        The puzzle where the attempt was made for
     * @param gameSessionId The session of the game that is being played
     * @param at            The time when the attempt happened
     * @return A boolean indicating the operation was successful or a service error indicating their was no game being played
     */
    ServiceActionResponse<Boolean> puzzleAttemptSuccessful(Puzzle puzzle, int gameSessionId, LocalDateTime at);

    /**
     * Do a failed puzzle attempt for a game session being played
     *
     * @param puzzle        The puzzle where the attempt was made for
     * @param gameSessionId The session of the game that is being played
     * @param at            The time when the attempt happened
     * @return A boolean indicating the operation was successful or a service error indicating their was no game being played
     */
    ServiceActionResponse<Boolean> puzzleAttemptFailed(Puzzle puzzle, int gameSessionId, LocalDateTime at);

    // QUERIES

    /**
     * Get all the games known to the system
     *
     * @return A list containing the games or an empty list
     */
    ServiceActionResponse<List<Game>> getAllGames();

    /**
     * Get all the puzzle devices in a game
     *
     * @param game The game
     * @return A GamesWithPuzzlesDto
     */
    ServiceActionResponse<GameWithPuzzlesDto> getAllPuzzlesInAGame(Game game);

    /**
     * Get all the game session for a game in the system
     *
     * @param game The game
     * @return A GameWithsessionsDto
     */
    ServiceActionResponse<GameWithSessionsDto> getAllGameSessions(Game game);

    /**
     * For a game that is being played, get it's session
     *
     * @return The game session or a service error indicating that no game is being played
     */
    ServiceActionResponse<GameSession> getCurrentlyPlayedGameSession(Game game);

    /**
     * Get the game progress of the game that is currently being played
     *
     * @param game The game that is active
     * @return ...
     */
    ServiceActionResponse<GameProgress> getGameProgress(Game game);
}
