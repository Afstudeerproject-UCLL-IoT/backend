package com.ucll.afstudeer.IoT.service.device.handlers;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;

public class RegisterDeviceWithPuzzleHandler {

    public static ServiceActionResponse<Device> handle(Device device,
                                                       DeviceRepository deviceRepository,
                                                       PuzzleRepository puzzleRepository) {
        // null check
        if (device == null)
            throw new IllegalArgumentException("Device cannot be null");

        // device with puzzle already exists
        if (puzzleRepository.exists(device.getPuzzle().getName())) {
            return new ServiceActionResponse<>("The device is already registered");
        }

        // Notify that the device is registered (TODO)

        //persist device with puzzle and get it back with the id
        var addedDevice = deviceRepository.addDeviceWithPuzzle(device);
        return new ServiceActionResponse<>(addedDevice);
    }
}
