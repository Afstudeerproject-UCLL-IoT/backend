package core.usecases.game;

import core.interfaces.NotificationService;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.GameRepository;
import core.interfaces.repositories.PuzzleRepository;
import core.usecases.device.DeviceUseCasesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameUseCasesBase {

    protected GameRepository gameRepository;
    protected GameUseCases gameUseCases;

    @BeforeEach
    public void setUp(){
        gameRepository = Mockito.mock(GameRepository.class);

        gameUseCases = new GameUseCaseImpl(gameRepository);

    }
}
