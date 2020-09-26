package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import core.interfaces.NotificationService;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;
import core.usecases.device.command.PuzzleIsCompletedCommand;
import core.usecases.device.command.RegisterDeviceWithPuzzleCommand;
import core.usecases.device.command.SubscribeToPuzzleCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class DeviceUseCasesImpl implements DeviceUseCases{
    private final DeviceRepository deviceRepository;
    private final PuzzleRepository puzzleRepository;
    private final NotificationService notificationService;

    public DeviceUseCasesImpl(DeviceRepository deviceRepository,
                              PuzzleRepository puzzleRepository,
                              NotificationService notificationService) {
        this.deviceRepository = deviceRepository;
        this.puzzleRepository = puzzleRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Device registerDeviceWithPuzzle(String deviceName) {
        return RegisterDeviceWithPuzzleCommand.handle(deviceName, deviceRepository, puzzleRepository);
    }

    @Override
    public ImmutablePair<Device, Puzzle> subscribeToPuzzle(String subscriberDeviceName, String puzzleName) {
        return SubscribeToPuzzleCommand.handle(subscriberDeviceName, puzzleName, deviceRepository, puzzleRepository);
    }

    @Override
    public void puzzleIsCompleted(String puzzleName) {
        PuzzleIsCompletedCommand.handle(puzzleName, puzzleRepository, notificationService);
    }
}
