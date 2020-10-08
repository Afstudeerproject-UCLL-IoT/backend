package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceService {

    // actions

    // register a device with a puzzle, get the device back with the id set
    ServiceActionResponse<Device> registerDeviceWithPuzzle(Device device);

    ServiceActionResponse<Device> registerFeedbackDevice(Device device);

    // the puzzle is completed
    ServiceActionResponse<Boolean> puzzleIsCompleted(Puzzle puzzle);

    ServiceActionResponse<Puzzle> updatePuzzleSolution(Puzzle puzzle, String newSolution);

    ServiceActionResponse<ConnectionActivity> deviceOnline(Device device, LocalDateTime onlineAt);

    // device became offline
    ServiceActionResponse<ConnectionActivity> deviceOffline(Device device, LocalDateTime offlineAt);

    // queries
    ServiceActionResponse<List<Device>> getAllDevicesWithPuzzleHandler();

    ServiceActionResponse<List<ConnectionActivity>> getAllConnectionActivity(Device device);

    ServiceActionResponse<List<Device>> getAllDevices();
}
