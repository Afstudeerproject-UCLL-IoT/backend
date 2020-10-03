package com.ucll.afstudeer.IoT.service.device.handlers;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class RegisterDeviceWithPuzzleHandler {

    public static ServiceActionResponse<Device> handle(Device device, DeviceRepository deviceRepository){
        // null check
        if(device == null)
            throw new IllegalArgumentException("Device cannot be null");

        // if the device already exists do a connection activity
        var foundDevice = deviceRepository.get(device.getId());
        if(foundDevice != null){
            // TODO log connection
            return new ServiceActionResponse<>(foundDevice);
        }

        // Notify that the device is registered (TODO)

        //persist device with puzzle and get it back with the id
        var addedDevice = deviceRepository.addDeviceWithPuzzle(device);
        return new ServiceActionResponse<>(addedDevice);
    }
}
