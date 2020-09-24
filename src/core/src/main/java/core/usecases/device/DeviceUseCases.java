package core.usecases.device;

import core.domain.device.Device;
import core.domain.puzzle.Puzzle;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface DeviceUseCases {
    Device registerDeviceWithPuzzle(String deviceName);
    ImmutablePair<Device, Puzzle> subscribeToPuzzle(String subscriberDeviceName, String puzzleName);
}
