package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class RegisterFeedbackDeviceHandler {

    public static ServiceActionResponse<Device> handle(Device device, DeviceRepository deviceRepository){
        // null check
        if (device == null)
            throw new IllegalArgumentException("Device cannot be null for feedback");

        // their can only be one feedback device check if it's there
        var foundFeedbackDevice = deviceRepository.getFeedbackDevice();
        if (foundFeedbackDevice != null) {
            return new ServiceActionResponse<>(foundFeedbackDevice);
        }

        // persist feedback device
        var addedFeedbackDevice = deviceRepository.addFeedbackDevice(device);
        return new ServiceActionResponse<>(addedFeedbackDevice);
    }
}
