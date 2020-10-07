package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.List;

public class GetAllDevicesWithPuzzleHandler {

    public static ServiceActionResponse<List<Device>> handle(DeviceRepository deviceRepository) {
        var devices = deviceRepository.getAllDevicesWithPuzzles();

        return new ServiceActionResponse<>(devices);
    }
}
