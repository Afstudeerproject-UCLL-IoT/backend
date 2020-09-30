package com.ucll.afstudeer.IoT.service.game.handlers;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.exception.game.CannotBecomeFirstPuzzleDeviceOfGameException;
import com.ucll.afstudeer.IoT.exception.subscribe.DeviceCannotSubscribeToPuzzleException;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;

public class AddFirstDevicePuzzleHandler {

    public static boolean handle(Game game, Device device, GameRepository gameRepository){
        // null checks
        if(game == null || device == null)
            throw new IllegalArgumentException("Game and device cannot be null for add first device puzzle");

        // check if it can become the first puzzle in the game
        if(!gameRepository.firstDevicePuzzleIsPossible(device, game))
            throw new CannotBecomeFirstPuzzleDeviceOfGameException();

        // add first game
        gameRepository.addGamePuzzleSubscription(device, null, game, 1);

        return true;
    }
}
