package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Game;
import com.ucll.afstudeer.IoT.domain.GameSession;
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
    public ServiceActionResponse<Game> createGame(Game game) {
        return CreateGameHandler.handle(game, gameRepository);
    }

    @Override
    public ServiceActionResponse<GameSession> startGame(Game game) {
        return StartGameHandler.handle(game, gameRepository, notificationService);
    }

    @Override
    public ServiceActionResponse<GameSession> endGame(Game game) {
        return null;
    }

    @Override
    public ServiceActionResponse<Boolean> addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle, int position) {
        return AddPuzzleSubscriptionHandler.handle(game, subscriber, puzzle, position, gameRepository);
    }

    @Override
    public ServiceActionResponse<List<GameDto>> getAllGames() {
        return GetAllGamesHandler.handle(gameRepository);
    }

    @Override
    public ServiceActionResponse<GameWithPuzzlesDto> getAllPuzzlesInAGame(String gameName) {
        return GetAllPuzzlesInAGameHandler.handle(gameName, gameRepository);
    }
}
