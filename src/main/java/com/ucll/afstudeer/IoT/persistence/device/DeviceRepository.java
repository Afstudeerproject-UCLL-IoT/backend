package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

public interface DeviceRepository extends GenericRepository<Device> {
    Device addDeviceWithPuzzle(Device device);
}
