package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.device.handlers.*;
import com.ucll.afstudeer.IoT.service.game.handlers.PuzzleAttemptSuccessfulHandler;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public ServiceActionResponse<Device> registerFeedbackDevice(Device device) {
        return RegisterFeedbackDeviceHandler.handle(device, deviceRepository);
    }

    @Override
    public ServiceActionResponse<Puzzle> updatePuzzleSolution(Puzzle puzzle, String newSolution) {
        return UpdatePuzzleSolutionHandler.handle(puzzle, newSolution, puzzleRepository, notificationService);
    }

    @Override
    public ServiceActionResponse<ConnectionActivity> deviceOnline(Device device, LocalDateTime onlineAt) {
        return DeviceOnlineHandler.handle(device, onlineAt, deviceRepository);
    }

    @Override
    public ServiceActionResponse<ConnectionActivity> deviceOffline(Device device, LocalDateTime offlineAt) {
        return DeviceOfflineHandler.handle(device, offlineAt, deviceRepository);
    }

    @Override
    public ServiceActionResponse<List<Device>> getAllDevicesWithPuzzleHandler() {
        return GetAllDevicesWithPuzzleHandler.handle(deviceRepository);
    }

    @Override
    public ServiceActionResponse<List<ConnectionActivity>> getAllConnectionActivity(Device device) {
        return GetAllConnectionActivityHandler.handle(device, deviceRepository);
    }

    @Override
    public ServiceActionResponse<List<Device>> getAllDevices() {
        return GetAllDevicesHandler.handle(deviceRepository);
    }
}
