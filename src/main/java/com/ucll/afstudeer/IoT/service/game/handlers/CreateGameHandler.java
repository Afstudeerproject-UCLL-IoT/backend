package com.ucll.afstudeer.IoT.service.game.handlers;


import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.exception.game.GameAlreadyExistsException;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;

public class CreateGameHandler {

    public static boolean handle(Game game, GameRepository gameRepository){
        // null check
        if(game == null)
            throw new IllegalArgumentException("Game cannot be null");

        // check if the game does not exist already
        if(gameRepository.isPresent(game))
            throw new GameAlreadyExistsException();

        // persist the game
        gameRepository.add(game);

        return true;
    }

}
