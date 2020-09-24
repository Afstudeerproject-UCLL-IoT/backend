package core.usecases.device;

import core.domain.device.Device;
import core.interfaces.DeviceRepository;
import core.interfaces.PuzzleRepository;
import core.usecases.device.command.RegisterDeviceWithPuzzleCommand;

public class DeviceUseCasesImpl implements DeviceUseCases{
    private final DeviceRepository deviceRepository;
    private final PuzzleRepository puzzleRepository;

    public DeviceUseCasesImpl(DeviceRepository deviceRepository, PuzzleRepository puzzleRepository) {
        this.deviceRepository = deviceRepository;
        this.puzzleRepository = puzzleRepository;
    }

    @Override
    public Device registerDeviceWithPuzzle(String deviceName) {
        return RegisterDeviceWithPuzzleCommand.handle(deviceName, deviceRepository, puzzleRepository);
    }
}
