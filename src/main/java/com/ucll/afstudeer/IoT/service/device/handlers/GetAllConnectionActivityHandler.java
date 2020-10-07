package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.ConnectionActivity;
import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.List;

public class GetAllConnectionActivityHandler {

    public static ServiceActionResponse<List<ConnectionActivity>> handle(Device device, DeviceRepository deviceRepository){
        if(device == null)
            throw new IllegalArgumentException("Null device not allowed");

        var result = deviceRepository.getConnectionActivity(device);
        return new ServiceActionResponse<>(result);
    }
}
