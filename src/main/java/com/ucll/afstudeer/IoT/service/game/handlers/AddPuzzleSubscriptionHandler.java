package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.exception.subscribe.CannotSubscribeToItselfException;
import com.ucll.afstudeer.IoT.exception.subscribe.DeviceCannotSubscribeToPuzzleException;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;

public class AddPuzzleSubscriptionHandler {

    public static boolean handle(Game game,
                                 Device subscriber,
                                 Puzzle puzzle,
                                 GameRepository gameRepository){
        // null checks
        if(game == null || subscriber == null)
            throw new IllegalArgumentException("For a subscription the subscriber and game cannot be null");

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
