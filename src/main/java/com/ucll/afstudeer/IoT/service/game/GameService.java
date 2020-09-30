package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;

import java.time.LocalDateTime;

public interface GameService {
    boolean createGame(Game game);

    boolean startGame(Game game);

    LocalDateTime endGame(Game game);

    boolean addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle);

    boolean addFirstDevicePuzzle(Game game, Device device);
}
