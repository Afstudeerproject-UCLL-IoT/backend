package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.domain.PuzzleSubscription;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class AddPuzzleSubscriptionHandler {

    public static ServiceActionResponse<Boolean> handle(Game game,
                                                        PuzzleSubscription subscription,
                                                        GameRepository gameRepository) {
        // null checks
        if (game == null || subscription == null)
            throw new IllegalArgumentException("For a subscription the subscriber and game cannot be null");

        // check if it's not trying to subscribe to itself
        if (subscription.getSubscriber().getPuzzle().equals(subscription.getPuzzle()))
            return ServiceActionResponse.Fail("A device cannot subscribe to it's own puzzle");

        // check if the subscription is possible (all entities exist)
        if (!gameRepository.gamePuzzleSubscriptionIsPossible(subscription.getSubscriber(), subscription.getPuzzle(), game))
            return ServiceActionResponse.Fail("The device cannot subscribe to the puzzle for a game because not all entities exist");

        // subscribe
        gameRepository.addGamePuzzleSubscription(subscription.getSubscriber(), subscription.getPuzzle(), game, subscription.getPosition());
        return ServiceActionResponse.Success();
    }
}
