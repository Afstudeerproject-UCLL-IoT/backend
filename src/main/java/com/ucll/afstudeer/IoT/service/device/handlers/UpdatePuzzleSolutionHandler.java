package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.domain.constant.Event;
import com.ucll.afstudeer.IoT.domain.constant.ServiceError;
import com.ucll.afstudeer.IoT.persistence.device.DeviceRepository;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

public class UpdatePuzzleSolutionHandler {

    public static ServiceActionResponse<Puzzle> handle(Puzzle puzzle,
                                                       String newSolution,
                                                       DeviceRepository deviceRepository,
                                                       PuzzleRepository puzzleRepository,
                                                       NotificationService notificationService) {
        // null checks
        if (puzzle == null || newSolution == null)
            throw new IllegalArgumentException("Changing a puzzle solution requires a non null puzzle and solution");

        // update the solution
        var updatedPuzzle = puzzleRepository.updatePuzzleSolution(puzzle, newSolution);

        // check if the puzzle was updated
        if (updatedPuzzle == null)
            return new ServiceActionResponse<>(ServiceError.PUZZLE_DOES_NOT_EXIST);

        // get the device
        var device = deviceRepository.getDeviceByPuzzle(puzzle);

        // check if the device was found
        if (device == null)
            return new ServiceActionResponse<>(ServiceError.DEVICE_DOES_NOT_EXIST);

        // send a notification that the solution has been updated
        notificationService.send(device, Event.NEWSOL, String.format("%s_%s", updatedPuzzle.getName(), updatedPuzzle.getSolution()));
        return new ServiceActionResponse<>(updatedPuzzle);
    }
}
