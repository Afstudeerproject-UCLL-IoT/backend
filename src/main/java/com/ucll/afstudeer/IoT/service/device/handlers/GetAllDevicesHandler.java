package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

import java.util.ArrayList;
import java.util.List;

public class GetAllDevicesHandler {

    public static ServiceActionResponse<List<Device>> handle(DeviceRepository deviceRepository) {
        // get the data
        var devicesWithPuzzles = deviceRepository.getAllDevicesWithPuzzles();
        var feedbackDevice = deviceRepository.getFeedbackDevice();

        // combine
        devicesWithPuzzles.add(feedbackDevice);

        return new ServiceActionResponse<>(new ArrayList<>(devicesWithPuzzles));
    }
}
