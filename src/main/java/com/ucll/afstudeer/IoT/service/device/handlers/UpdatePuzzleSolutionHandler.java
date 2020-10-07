package com.ucll.afstudeer.IoT.service.device.handlers;

import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.puzzle.PuzzleRepository;
import com.ucll.afstudeer.IoT.service.ServiceActionResponse;
import com.ucll.afstudeer.IoT.service.notification.NotificationService;

public class UpdatePuzzleSolutionHandler {

    public static ServiceActionResponse<Puzzle> handle(Puzzle puzzle,
                                                       String newSolution,
                                                       PuzzleRepository puzzleRepository,
                                                       NotificationService notificationService){
        // null checks
        if(puzzle == null || newSolution == null)
            throw new IllegalArgumentException("Changing a puzzle solution requires a non null puzzle and solution");

        // update the solution
        var updatedPuzzle = puzzleRepository.updatePuzzleSolution(puzzle, newSolution);

        // TODO send notification that puzzle has new solution

        return new ServiceActionResponse<>(updatedPuzzle);
    }
}
