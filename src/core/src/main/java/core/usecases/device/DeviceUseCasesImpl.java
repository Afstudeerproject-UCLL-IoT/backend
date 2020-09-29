package core.usecases.device;

import core.domain.Device;
import core.domain.Puzzle;
import core.interfaces.NotificationService;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;
import core.usecases.device.command.PuzzleIsCompletedCommand;
import core.usecases.device.command.RegisterDeviceWithPuzzleCommand;

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
    public Device registerDeviceWithPuzzle(Device device) {
        return RegisterDeviceWithPuzzleCommand.handle(device, deviceRepository);
    }

    @Override
    public void puzzleIsCompleted(Puzzle puzzle) {
        PuzzleIsCompletedCommand.handle(puzzle, puzzleRepository, notificationService);
    }
}
