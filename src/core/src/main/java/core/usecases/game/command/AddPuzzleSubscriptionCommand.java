package core.usecases.game.command;

import core.domain.Device;
import core.domain.Game;
import core.domain.Puzzle;
import core.exceptions.subscribe.CannotSubscribeToItselfException;
import core.exceptions.subscribe.DeviceCannotSubscribeToPuzzleException;
import core.interfaces.repositories.GameRepository;

public class AddPuzzleSubscriptionCommand {

    public static boolean handle(Game game,
                                 Device subscriber,
                                 Puzzle puzzle,
                                 GameRepository gameRepository){
        // null checks
        if(game == null || subscriber == null || puzzle == null)
            throw new IllegalArgumentException("For a subscription the subscriber, puzzle and game cannot be null");

        // check if it's not trying to subscribe to itself
        if(subscriber.getPuzzle().equals(puzzle))
            throw new CannotSubscribeToItselfException();

        // check if the subscription is possible (all entities exist)
        if(!gameRepository.GamePuzzleSubscriptionIsPossible(subscriber, puzzle, game))
            throw new DeviceCannotSubscribeToPuzzleException();

        // subscribe
        gameRepository.addGamePuzzleSubscription(subscriber, puzzle, game);

        return true;
    }
}
