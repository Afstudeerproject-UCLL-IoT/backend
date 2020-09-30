package com.ucll.afstudeer.IoT.web.controller;

import com.ucll.afstudeer.IoT.dto.GameDto;
import com.ucll.afstudeer.IoT.dto.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.service.game.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameDto> getAllGames(){
        return gameService.getAllGames();
    }

    @GetMapping("/{gameName}/puzzle")
    public GameWithPuzzlesDto getAllPuzzlesInAGame(@PathVariable String gameName){
        return gameService.getAllPuzzlesInAGame(gameName);
    }
}
