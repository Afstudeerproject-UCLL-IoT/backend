package web.configuration;

import core.interfaces.NotificationService;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;
import core.usecases.device.DeviceUseCases;
import core.usecases.device.DeviceUseCasesImpl;
import infrastructure.di.NotificationConfiguration;
import infrastructure.di.PersistenceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.WebSocketSession;

@Configuration
@Import({PersistenceConfiguration.class, NotificationConfiguration.class})
public class UseCasesConfiguration {

    @Bean
    public DeviceUseCases deviceUseCases(
            PuzzleRepository puzzleRepository,
            DeviceRepository deviceRepository,
            NotificationService<WebSocketSession> notificationService
    ){
        return new DeviceUseCasesImpl(deviceRepository, puzzleRepository, notificationService);
    }
}
