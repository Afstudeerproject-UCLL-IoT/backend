package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameServiceBase {

    protected GameRepository gameRepository;
    protected NotificationService notificationService;

    protected GameService gameService;

    @BeforeEach
    public void setUp() {
        gameRepository = Mockito.mock(GameRepository.class);
        notificationService = Mockito.mock(NotificationService.class);

        gameService = new GameServiceImpl(gameRepository, notificationService);

    }
}
