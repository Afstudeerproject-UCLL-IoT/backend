package core.usecases.device;

import core.domain.device.Device;
import core.domain.puzzle.Puzzle;
import core.interfaces.DeviceRepository;
import core.interfaces.PuzzleRepository;
import core.usecases.device.command.RegisterDeviceWithPuzzleCommand;
import core.usecases.device.command.SubscribeToPuzzleCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

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

    @Override
    public ImmutablePair<Device, Puzzle> subscribeToPuzzle(String subscriberDeviceName, String puzzleName) {
        return SubscribeToPuzzleCommand.handle(subscriberDeviceName, puzzleName, deviceRepository, puzzleRepository);
    }
}
