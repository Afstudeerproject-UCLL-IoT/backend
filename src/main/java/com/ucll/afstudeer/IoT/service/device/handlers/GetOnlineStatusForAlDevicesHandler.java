package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.dto.out.DeviceWithOnlineStatus;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.List;

public class GetOnlineStatusForAlDevicesHandler {

    public static ServiceActionResponse<List<DeviceWithOnlineStatus>> handle(DeviceRepository deviceRepository) {
        var result = deviceRepository.getOnlineStatuses();

        return new ServiceActionResponse<>(result);
    }
}
