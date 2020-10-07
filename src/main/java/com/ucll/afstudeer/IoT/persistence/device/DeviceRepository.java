package com.ucll.afstudeer.IoT.persistence.device;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceRepository extends GenericRepository<Device, Integer> {
    Device addDeviceWithPuzzle(Device device);

    List<Device> getAllDevicesWithPuzzles();

    ConnectionActivity addDeviceConnectionActivity(Device device, LocalDateTime online);

    ConnectionActivity setLastDeviceConnectionActivityOfflineTime(Device device, LocalDateTime offline);

    List<ConnectionActivity> getConnectionActivity(Device device);
}
