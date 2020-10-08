package com.ucll.afstudeer.IoT.service.device.handlers;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class RegisterDeviceWithPuzzleHandler {

    public static ServiceActionResponse<Device> handle(Device device,
                                                       DeviceRepository deviceRepository) {
        // null check
        if (device == null || device.getPuzzle() == null)
            throw new IllegalArgumentException("Device or puzzle cannot be null");

        // find the device by puzzle name if it exists return it
        var foundDevice = deviceRepository.getDeviceByPuzzle(device.getPuzzle());
        if (foundDevice != null) {
            return new ServiceActionResponse<>(foundDevice);
        }

        //persist device with puzzle and get it back with the id
        var addedDevice = deviceRepository.addDeviceWithPuzzle(device);
        return new ServiceActionResponse<>(addedDevice);
    }
}
