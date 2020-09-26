package core.usecases.device;

import core.interfaces.NotificationService;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeviceUseCasesBase {

    protected DeviceUseCases deviceUseCases;
    protected DeviceRepository deviceRepository;
    protected PuzzleRepository puzzleRepository;
    protected NotificationService notificationService;

    @BeforeEach
    public void setUp(){
        deviceRepository = Mockito.mock(DeviceRepository.class);
        puzzleRepository = Mockito.mock(PuzzleRepository.class);
        deviceUseCases = new DeviceUseCasesImpl(deviceRepository, puzzleRepository, notificationService);
    }
}
