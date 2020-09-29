package core.usecases.game.command;

import core.domain.Game;
import core.exceptions.game.GameAlreadyExistsException;
import core.interfaces.repositories.GameRepository;

public class CreateGameCommand {

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
