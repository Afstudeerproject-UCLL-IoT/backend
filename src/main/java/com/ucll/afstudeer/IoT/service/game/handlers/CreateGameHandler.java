package com.ucll.afstudeer.IoT.service.game.handlers;


import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;

public class CreateGameHandler {

    public static Game handle(Game game, GameRepository gameRepository){
        // null check
        if(game == null)
            throw new IllegalArgumentException("Game cannot be null");

        // return the game back if it already exists
        var foundGame = gameRepository.get(game.getName());
        if(foundGame != null)
            return foundGame;

        // persist the game and return it
        return gameRepository.add(game);
    }

}
