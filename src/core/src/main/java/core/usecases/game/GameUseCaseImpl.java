package core.usecases.game;

import core.domain.Device;
import core.domain.Game;
import core.domain.Puzzle;
import core.interfaces.NotificationService;
import core.interfaces.repositories.GameRepository;
import core.usecases.game.command.AddPuzzleSubscriptionCommand;
import core.usecases.game.command.CreateGameCommand;
import core.usecases.game.command.StartGameCommand;

import java.time.LocalDateTime;

public class GameUseCaseImpl implements GameUseCases {

    private final GameRepository gameRepository;
    private final NotificationService notificationService;

    public GameUseCaseImpl(GameRepository gameRepository, NotificationService notificationService) {
        this.gameRepository = gameRepository;
        this.notificationService = notificationService;
    }

    @Override
    public boolean createGame(Game game) {
        return CreateGameCommand.handle(game, gameRepository);
    }

    @Override
    public boolean startGame(Game game) {
        return StartGameCommand.handle(game, gameRepository, notificationService);
    }

    @Override
    public LocalDateTime endGame(Game game) {
        return null;
    }

    @Override
    public boolean addPuzzleSubscription(Game game, Device subscriber, Puzzle puzzle) {
        return AddPuzzleSubscriptionCommand.handle(game, subscriber, puzzle, gameRepository);
    }
}
