package com.ucll.afstudeer.IoT.service.game;

import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.game.GameRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameServiceBase {

    protected DeviceRepository deviceRepository;
    protected GameRepository gameRepository;
    protected PuzzleRepository puzzleRepository;
    protected NotificationService notificationService;

    protected GameService gameService;

    @BeforeEach
    public void setUp() {
        deviceRepository = Mockito.mock(DeviceRepository.class);
        gameRepository = Mockito.mock(GameRepository.class);
        puzzleRepository = Mockito.mock(PuzzleRepository.class);
        notificationService = Mockito.mock(NotificationService.class);

        gameService = new GameServiceImpl(deviceRepository, gameRepository, puzzleRepository, notificationService);

    }
}
