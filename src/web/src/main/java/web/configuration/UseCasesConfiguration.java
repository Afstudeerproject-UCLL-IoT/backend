package web.configuration;

import core.interfaces.NotificationService;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.GameRepository;
import core.interfaces.repositories.PuzzleRepository;
import core.usecases.device.DeviceUseCases;
import core.usecases.device.DeviceUseCasesImpl;
import core.usecases.game.GameUseCaseImpl;
import core.usecases.game.GameUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

@Configuration
@ComponentScan({"web", "infrastructure"})
public class UseCasesConfiguration {


    // TODO use components instead of manually defining beans
    // maybe add the implementation of the usecases in infrastructure?
    // don't want core to depend on spring DI but maybe thats fine?

    @Bean
    public DeviceUseCases deviceUseCases(
            DeviceRepository deviceRepository,
            PuzzleRepository puzzleRepository,
            NotificationService<WebSocketSession> notificationService
    ){
        return new DeviceUseCasesImpl(deviceRepository, puzzleRepository, notificationService);
    }

    @Bean
    public GameUseCases gameUseCases(
            GameRepository gameRepository,
            NotificationService<WebSocketSession> notificationService
    ){
        return new GameUseCaseImpl(gameRepository, notificationService);
    }
}
