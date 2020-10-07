package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.PuzzleSubscription;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.service.game.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService
                .getAllGames()
                .getValue();
    }

    @GetMapping("/{gameName}/puzzle")
    public GameWithPuzzlesDto getAllPuzzlesInAGame(@PathVariable String gameName) {
        return gameService
                .getAllPuzzlesInAGame(gameName)
                .getValue();
    }

    @PostMapping("/{gameName}")
    public boolean addPuzzleSubscriptions(@PathVariable String gameName, @RequestBody List<PuzzleSubscription> subscriptions){
        var game = new Game.Builder().withName(gameName).build();

        return gameService.addPuzzleSubscriptions(game, subscriptions)
                .getValue();
    }
}
