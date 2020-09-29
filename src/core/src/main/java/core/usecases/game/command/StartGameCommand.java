package core.usecases.game.command;

import core.domain.Event;
import core.domain.Game;
import core.domain.GameSession;
import core.exceptions.game.GameDoesNotExistException;
import core.interfaces.NotificationService;
import core.interfaces.repositories.GameRepository;

import java.time.LocalDateTime;

public class StartGameCommand {

    public static boolean handle(Game game, GameRepository gameRepository, NotificationService notificationService){
        // null check
        if(game == null)
            throw new IllegalArgumentException("Game cannot be null");

        // check if the game that is going to start exists
        if(!gameRepository.exists(game)){
            throw new GameDoesNotExistException();
        }

        // create a new game session
        var session = new GameSession.Builder()
                .withoutId()
                .withStartTime(LocalDateTime.now())
                .withEndTime(null)
                .build();

        // persist game with session
        gameRepository.addGameSession(game, session);

        // get the first device with puzzle in the game
        var device = gameRepository.getFirstDevicePuzzle(game);

        // notify the device that the game has started
        notificationService.send(device, Event.GAME_STARTED);

        return true;
    }
}
