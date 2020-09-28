package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;

public interface DeviceUseCases {
    boolean registerDeviceWithPuzzle(Device device);
    boolean subscribeToPuzzle(Device subscriber, Puzzle puzzle);
    void puzzleIsCompleted(Puzzle puzzle);
}
