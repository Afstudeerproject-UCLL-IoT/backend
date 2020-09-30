package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.util.List;

public interface DeviceRepository extends GenericRepository<Device> {
    Device addDeviceWithPuzzle(Device device);

    List<Device> getAllDevicesWithPuzzles();
}
