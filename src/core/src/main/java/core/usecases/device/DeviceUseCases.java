package core.usecases.device;

import core.domain.device.Device;

public interface DeviceUseCases {
    Device registerDeviceWithPuzzle(String deviceName);
}
