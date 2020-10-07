package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
import com.ucll.afstudeer.IoT.domain.PuzzleSubscription;
import com.ucll.afstudeer.IoT.dto.in.GameSessionEndTime;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.dto.out.GameWithSessionsDto;
import com.ucll.afstudeer.IoT.service.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Operation(summary = "Add puzzle subscriptions from a list containing the subscriptions")
    @PostMapping("/{gameName}")
    public boolean addPuzzleSubscriptions(@PathVariable String gameName, @RequestBody List<PuzzleSubscription> subscriptions){
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
    public GameSession startGame(@PathVariable String gameName){
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
    public GameSession startGame(@PathVariable String gameName, @Valid @RequestBody GameSessionEndTime gameSessionEndTime){
        // create game
        var game = new Game.Builder()
                .withName(gameName)
                .build();

        // start game
        return gameService.endGame(game, gameSessionEndTime.getValue())
                .getValue();
    }
}
