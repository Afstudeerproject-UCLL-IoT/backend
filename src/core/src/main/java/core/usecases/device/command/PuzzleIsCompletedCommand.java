package core.usecases.device.command;

import core.domain.Event;
import core.domain.Puzzle;
import core.interfaces.NotificationService;
import core.interfaces.repositories.PuzzleRepository;

public class PuzzleIsCompletedCommand {

    public static void handle(Puzzle puzzle,
                              PuzzleRepository puzzleRepository,
                              NotificationService notificationService){

        // null check
        if(puzzle == null) throw new IllegalArgumentException("Puzzle cannot be null");

        // find the devices that are subscribed to this puzzle
        var devices = puzzleRepository.getSubscriptions(puzzle);

        // send a notification that the puzzle is completed
        devices.forEach(device ->
                notificationService.send(device, Event.PUZZLE_COMPLETED));
    }
}
