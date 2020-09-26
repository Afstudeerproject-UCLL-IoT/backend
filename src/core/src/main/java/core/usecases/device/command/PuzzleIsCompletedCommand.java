package core.usecases.device.command;

import core.domain.Device;
import core.domain.Event;
import core.interfaces.NotificationService;
import core.interfaces.repositories.PuzzleRepository;

public class PuzzleIsCompletedCommand {

    public static void handle(String deviceName,
                              PuzzleRepository puzzleRepository,
                              NotificationService notificationService){
        // create device
        var device = Device.instance(deviceName);

        // find the devices that are subscribed to this puzzle
        var devices = puzzleRepository.getSubscriptions(device.getPuzzle());

        // send a notification that the puzzle is completed
        notificationService.send(devices, Event.PUZZLE_COMPLETED);
    }
}
