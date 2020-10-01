package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.DevicePuzzleDto;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.device.handlers.GetAllDevicesWithPuzzleHandler;
import com.ucll.afstudeer.IoT.service.device.handlers.PuzzleIsCompletedHandler;
import com.ucll.afstudeer.IoT.service.device.handlers.RegisterDeviceWithPuzzleHandler;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final PuzzleRepository puzzleRepository;
    private final NotificationService notificationService;

    public DeviceServiceImpl(DeviceRepository deviceRepository,
                             PuzzleRepository puzzleRepository,
                             NotificationService notificationService) {
        this.deviceRepository = deviceRepository;
        this.puzzleRepository = puzzleRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Device registerDeviceWithPuzzle(Device device) {
        return RegisterDeviceWithPuzzleHandler.handle(device, deviceRepository);
    }

    @Override
    public void puzzleIsCompleted(Puzzle puzzle) {
        PuzzleIsCompletedHandler.handle(puzzle, puzzleRepository, notificationService);
    }

    @Override
    public List<DevicePuzzleDto> getAllDevicesWithPuzzleHandler() {
        return GetAllDevicesWithPuzzleHandler.handle(deviceRepository);
    }
}