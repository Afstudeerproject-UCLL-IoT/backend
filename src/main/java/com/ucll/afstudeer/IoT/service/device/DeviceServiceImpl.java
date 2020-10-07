package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.device.handlers.GetAllDevicesWithPuzzleHandler;
import com.ucll.afstudeer.IoT.service.device.handlers.PuzzleIsCompletedHandler;
import com.ucll.afstudeer.IoT.service.device.handlers.RegisterDeviceWithPuzzleHandler;
import com.ucll.afstudeer.IoT.service.device.handlers.UpdatePuzzleSolutionHandler;
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
    public ServiceActionResponse<Device> registerDeviceWithPuzzle(Device device) {
        return RegisterDeviceWithPuzzleHandler.handle(device, deviceRepository);
    }

    @Override
    public ServiceActionResponse<Boolean> puzzleIsCompleted(Puzzle puzzle) {
        return PuzzleIsCompletedHandler.handle(puzzle, puzzleRepository, notificationService);
    }

    @Override
    public ServiceActionResponse<Puzzle> updatePuzzleSolution(Puzzle puzzle, String newSolution) {
        return UpdatePuzzleSolutionHandler.handle(puzzle, newSolution, puzzleRepository, notificationService);
    }

    @Override
    public ServiceActionResponse<List<Device>> getAllDevicesWithPuzzleHandler() {
        return GetAllDevicesWithPuzzleHandler.handle(deviceRepository);
    }
}
