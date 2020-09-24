package core.usecases.device.command;

import core.domain.device.Device;
import core.exceptions.device.DeviceAlreadyExistsException;
import core.interfaces.DeviceRepository;
import core.interfaces.PuzzleRepository;

public class RegisterDeviceWithPuzzleCommand {

    public static Device handle(String deviceName, DeviceRepository deviceRepository, PuzzleRepository puzzleRepository){
        // create the device
        var device = Device.instance(deviceName);

        // if the device already exists throw an exception
        if(deviceRepository.exists(device)) throw new DeviceAlreadyExistsException();

        // Notify that the device is registered (TODO)

        //persist device and puzzle
        deviceRepository.add(device);
        puzzleRepository.add(device.getPuzzle());

        // return the device
        return device;
    }
}
