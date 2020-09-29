package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;

public interface DeviceUseCases {
    boolean registerDeviceWithPuzzle(Device device);

    void puzzleIsCompleted(Puzzle puzzle);
}
