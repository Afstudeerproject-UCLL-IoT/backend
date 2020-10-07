package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.domain.*;
import com.ucll.afstudeer.IoT.dto.out.GameWithPuzzlesDto;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.game.handlers.*;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.stereotype.Service;

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
    public ServiceActionResponse<Boolean> addPuzzleSubscription(Game game, PuzzleSubscription subscription){
        return AddPuzzleSubscriptionHandler.handle(game, subscription, gameRepository);
    }

    // TODO what if 1 subscription fails to add, try again?
    @Override
    public ServiceActionResponse<Boolean> addPuzzleSubscriptions(Game game, List<PuzzleSubscription> subscriptions) {
        var failed = subscriptions.stream()
                .map(subscription -> addPuzzleSubscription(game, subscription))
                .anyMatch(response -> !response.getValue()); // check is any response was status failed

        if(failed)
            return new ServiceActionResponse<>(false);

        return new ServiceActionResponse<>(true);
    }

    @Override
    public ServiceActionResponse<List<Game>> getAllGames() {
        return GetAllGamesHandler.handle(gameRepository);
    }

    @Override
    public ServiceActionResponse<GameWithPuzzlesDto> getAllPuzzlesInAGame(String gameName) {
        return GetAllPuzzlesInAGameHandler.handle(gameName, gameRepository);
    }
}
