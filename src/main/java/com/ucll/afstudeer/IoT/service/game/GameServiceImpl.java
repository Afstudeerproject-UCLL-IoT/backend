package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.GameDto;
import com.ucll.afstudeer.IoT.dto.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.game.handlers.*;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final NotificationService notificationService;

    public GameServiceImpl(GameRepository gameRepository, NotificationService notificationService) {
        this.gameRepository = gameRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Game createGame(Game game) {
        return CreateGameHandler.handle(game, gameRepository);
    }

    @Override
    public ServiceActionResponse startGame(Game game) {
        return StartGameHandler.handle(game, gameRepository, notificationService);
    }

    @Override
    public LocalDateTime endGame(Game game) {
        return null;
    }

    @Override
    public ServiceActionResponse addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle, int position) {
        return AddPuzzleSubscriptionHandler.handle(game, subscriber, puzzle, position, gameRepository);
    }

    @Override
    public boolean addFirstDevicePuzzle(Game game, Device device) {
        return AddFirstDevicePuzzleHandler.handle(game, device, gameRepository);
    }

    @Override
    public List<GameDto> getAllGames() {
        return GetAllGamesHandler.handle(gameRepository);
    }

    @Override
    public GameWithPuzzlesDto getAllPuzzlesInAGame(String gameName) {
        return GetAllPuzzlesInAGameHandler.handle(gameName, gameRepository);
    }
}
