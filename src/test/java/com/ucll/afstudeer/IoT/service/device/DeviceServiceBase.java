package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceBase {

    protected DeviceService deviceService;
    protected DeviceRepository deviceRepository;
    protected PuzzleRepository puzzleRepository;
    protected NotificationService notificationService;

    @BeforeEach
    public void setUp(){
        deviceRepository = Mockito.mock(DeviceRepository.class);
        puzzleRepository = Mockito.mock(PuzzleRepository.class);
        notificationService = Mockito.mock(NotificationService.class);

        deviceService = new DeviceServiceImpl(deviceRepository, puzzleRepository, notificationService);
    }
}
