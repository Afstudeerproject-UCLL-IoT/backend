package core.usecases.device.command;

import core.domain.Device;
import core.exceptions.device.DeviceAlreadyExistsException;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;

public class RegisterDeviceWithPuzzleCommand {

    public static boolean handle(Device device, DeviceRepository deviceRepository){
        // null check
        if(device == null)
            throw new IllegalArgumentException("Device cannot be null");

        // if the device already exists throw an exception
        if(deviceRepository.exists(device))
            throw new DeviceAlreadyExistsException();

        // Notify that the device is registered (TODO)

        //persist device and puzzle
        deviceRepository.addDeviceWithPuzzle(device);

        // return the device
        return true;
    }
}
