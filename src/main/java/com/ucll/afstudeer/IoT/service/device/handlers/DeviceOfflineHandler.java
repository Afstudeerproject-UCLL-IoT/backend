package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
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

        // set the offline time
        var connectionActivity = deviceRepository.setLastDeviceConnectionActivityOfflineTime(device, offlineAt);

        if(connectionActivity == null)
            return new ServiceActionResponse<>("Setting the offline time was not possible");

        return new ServiceActionResponse<>(connectionActivity);
    }
}
