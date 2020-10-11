package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.time.LocalDateTime;

public class DeviceOfflineHandler {

    public static ServiceActionResponse<ConnectionActivity> handle(Device device,
                                                                   LocalDateTime offlineAt,
                                                                   DeviceRepository deviceRepository){
        // null checks
        if(device == null || offlineAt == null)
            throw new IllegalArgumentException("Online activity requires a non null device at date time");

        // check if the device exists
        if(!deviceRepository.exists(device.getId()))
            return new ServiceActionResponse<>(ServiceError.DEVICE_DOES_NOT_EXIST);

        // set the offline time
        var connectionActivity = deviceRepository.setLastDeviceConnectionActivityOfflineTime(device, offlineAt);

        // check if the offline time was set
        if(connectionActivity == null)
            return new ServiceActionResponse<>(ServiceError.CONNECTION_ACTIVITY_OFFLINE_NOT_SET);

        return new ServiceActionResponse<>(connectionActivity);
    }
}
