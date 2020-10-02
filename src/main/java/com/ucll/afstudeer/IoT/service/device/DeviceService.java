package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.dto.DevicePuzzleDto;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.List;

public interface DeviceService {

    // actions

    // register a device with a puzzle, get the device back with the id set
    Device registerDeviceWithPuzzle(Device device);

    // the puzzle is completed
    ServiceActionResponse puzzleIsCompleted(Puzzle puzzle);

    // queries
    List<DevicePuzzleDto> getAllDevicesWithPuzzleHandler();
}
