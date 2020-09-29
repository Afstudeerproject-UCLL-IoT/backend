package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;

public interface DeviceUseCases {
    // register a device with a puzzle, get the device back with the id set
    Device registerDeviceWithPuzzle(Device device);

    void puzzleIsCompleted(Puzzle puzzle);
}
