package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.domain.*;
import com.ucll.afstudeer.IoT.dto.in.GameSessionEndTime;
import com.ucll.afstudeer.IoT.dto.in.NewGame;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.dto.out.GameWithSessionsDto;
import com.ucll.afstudeer.IoT.service.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Game calls")
@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Get all the available games")
    @GetMapping
    public List<Game> getAllGames() {
        return gameService
                .getAllGames()
                .getValue();
    }

    @Operation(summary = "Get all puzzles in a game in their completion order ascending")
    @GetMapping("/{gameName}/puzzle")
    public GameWithPuzzlesDto getAllPuzzlesInAGame(@PathVariable String gameName) {
        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        return gameService
                .getAllPuzzlesInAGame(game)
                .getValue();
    }

    @Operation(summary = "Get the progress of the game if it's being played")
    @GetMapping("/{gameName}/progress")
    public GameProgress getGameProgress(@PathVariable String gameName) {
        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        return gameService.getGameProgress(game)
                .getValue();
    }

    @Operation(summary = "Get all the sessions of a game")
    @GetMapping("/{gameName}/session")
    public GameWithSessionsDto getAllGameSessionsInAGame(@PathVariable String gameName) {
        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        return gameService
                .getAllGameSessions(game)
                .getValue();
    }

    @Operation(summary = "Create a new game")
    @PostMapping
    public Game createGame(@RequestBody NewGame newGame) {
        // create game
        var game = new Game.Builder()
                .withName(newGame.getGameName())
                .build();

        return gameService.createGame(game)
                .getValue();
    }

    @Operation(summary = "Add puzzle subscriptions from a list containing the subscriptions")
    @PostMapping("/{gameName}")
    public boolean addPuzzleSubscriptions(@PathVariable String gameName, @RequestBody List<PuzzleSubscription> subscriptions) {
        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        // add puzzle subscriptions
        return gameService.addPuzzleSubscriptions(game, subscriptions)
                .getValue();
    }

    @Operation(summary = "Start an existing game")
    @GetMapping("/{gameName}/start")
    public GameSession startGame(@PathVariable String gameName) {
        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        // start game
        return gameService.startGame(game)
                .getValue();
    }

    @Operation(summary = "Stop an existing game that is started")
    @PutMapping("/{gameName}/stop")
    public GameSession stopGame(@PathVariable String gameName, @Valid @RequestBody GameSessionEndTime gameSessionEndTime) {
        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        // start game
        return gameService.endGame(game, gameSessionEndTime.getValue())
                .getValue();
    }

    @PutMapping("/{gameName}/{puzzleName}/completed")
    @Operation(summary = "Complete a puzzle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Happens when their is no game currently being played"),
            @ApiResponse(responseCode = "200", description = "The puzzle is set to completed")
    }
    )
    public ResponseEntity<String> setThePuzzleToCompleted(@PathVariable String puzzleName, @PathVariable String gameName) {
        // time
        var at = LocalDateTime.now();

        // create puzzle
        var puzzle = new Puzzle.Builder()
                .withName(puzzleName)
                .withoutSolution()
                .build();

        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        // get current game that is being played (the session)
        var gameSession = gameService.getCurrentlyPlayedGameSession(game)
                .getValue();

        if (gameSession == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Game is not active: " + gameName);
        }

        gameService.puzzleAttemptSuccessful(puzzle, gameSession.getId(), at);
        return ResponseEntity.ok("");
    }
}
