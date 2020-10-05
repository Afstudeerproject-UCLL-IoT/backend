package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.Event;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

public class PuzzleIsCompletedHandler {

    public static ServiceActionResponse<Boolean> handle(Puzzle puzzle,
                                                        PuzzleRepository puzzleRepository,
                                                        NotificationService notificationService) {

        // null check
        if (puzzle == null)
            throw new IllegalArgumentException("Puzzle cannot be null");

        // find the devices that are subscribed to this puzzle
        var devices = puzzleRepository.getSubscriptions(puzzle);

        // send a notification that the puzzle is completed
        devices.forEach(device ->
                notificationService.send(device, Event.PUZZLE_COMPLETED));

        return ServiceActionResponse.Success();
    }
}
