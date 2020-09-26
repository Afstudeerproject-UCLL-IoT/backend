package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface DeviceUseCases {
    Device registerDeviceWithPuzzle(String deviceName);
    ImmutablePair<Device, Puzzle> subscribeToPuzzle(String subscriberDeviceName, String puzzleName);
    void puzzleIsCompleted(String deviceName);
}
