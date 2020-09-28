package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import core.interfaces.NotificationService;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface DeviceUseCases {
    Device registerDeviceWithPuzzle(Device device);
    ImmutablePair<Device, Puzzle> subscribeToPuzzle(Device subscriber, Puzzle puzzle);
    void puzzleIsCompleted(Puzzle puzzle);
}
