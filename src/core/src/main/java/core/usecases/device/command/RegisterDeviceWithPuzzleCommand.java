package core.usecases.device.command;

import core.domain.Device;
import core.exceptions.device.DeviceAlreadyExistsException;
import core.interfaces.repositories.DeviceRepository;

public class RegisterDeviceWithPuzzleCommand {

    public static Device handle(Device device, DeviceRepository deviceRepository){
        // null check
        if(device == null)
            throw new IllegalArgumentException("Device cannot be null");

        // if the device already exists throw an exception
        if(deviceRepository.isPresent(device))
            throw new DeviceAlreadyExistsException();

        // Notify that the device is registered (TODO)

        //persist device with puzzle and get it back with the id
        return deviceRepository.addDeviceWithPuzzle(device);
    }
}
