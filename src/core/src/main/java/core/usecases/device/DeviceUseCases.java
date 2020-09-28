package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import core.interfaces.NotificationService;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface DeviceUseCases {
    boolean registerDeviceWithPuzzle(Device device);
    boolean subscribeToPuzzle(Device subscriber, Puzzle puzzle);
    void puzzleIsCompleted(Puzzle puzzle);
}
