package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;

public class DeviceOnlineHandler {

    public static ServiceActionResponse<ConnectionActivity> handle(Device device,
                                                                   LocalDateTime onlineAt,
                                                                   DeviceRepository deviceRepository) {
        // null checks
        if (device == null || onlineAt == null)
            throw new IllegalArgumentException("Online activity requires a non null device at date time");

        // check if the device exists
        if (!deviceRepository.exists(device.getId()))
            return new ServiceActionResponse<>(ServiceError.DEVICE_DOES_NOT_EXIST);

        // persist online activity
        var connectionActivity = deviceRepository.addDeviceConnectionActivity(device, onlineAt);

        // check if it was added
        if (connectionActivity == null)
            return new ServiceActionResponse<>(ServiceError.CONNECTION_ACTIVITY_NOT_ADDED);

        return new ServiceActionResponse<>(connectionActivity);
    }
}
