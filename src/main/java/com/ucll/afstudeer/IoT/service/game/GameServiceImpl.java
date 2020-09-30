package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.game.handlers.AddFirstDevicePuzzleHandler;
import com.ucll.afstudeer.IoT.service.game.handlers.AddPuzzleSubscriptionHandler;
import com.ucll.afstudeer.IoT.service.game.handlers.CreateGameHandler;
import com.ucll.afstudeer.IoT.service.game.handlers.StartGameHandler;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final NotificationService notificationService;

    public GameServiceImpl(GameRepository gameRepository, NotificationService notificationService) {
        this.gameRepository = gameRepository;
        this.notificationService = notificationService;
    }

    @Override
    public boolean createGame(Game game) {
        return CreateGameHandler.handle(game, gameRepository);
    }

    @Override
    public boolean startGame(Game game) {
        return StartGameHandler.handle(game, gameRepository, notificationService);
    }

    @Override
    public LocalDateTime endGame(Game game) {
        return null;
    }

    @Override
    public boolean addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle) {
        return AddPuzzleSubscriptionHandler.handle(game, subscriber, puzzle, gameRepository);
    }

    @Override
    public boolean addFirstDevicePuzzle(Game game, Device device) {
        return AddFirstDevicePuzzleHandler.handle(game, device, gameRepository);
    }
}
