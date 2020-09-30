package com.ucll.afstudeer.IoT.service.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;

public interface DeviceService {
    // register a device with a puzzle, get the device back with the id set
    Device registerDeviceWithPuzzle(Device device);

    void puzzleIsCompleted(Puzzle puzzle);
}
