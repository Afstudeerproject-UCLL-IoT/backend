package com.ucll.afstudeer.IoT.persistence.puzzle;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.util.List;

public interface PuzzleRepository extends GenericRepository<Puzzle, String> {
    /**
     * Get all the devices that are subscribed to a puzzle
     *
     * @param puzzle The puzzle
     * @return A list containing the subscribed devices or an empty list when there are no subscriptions
     */
    List<Device> getSubscriptions(Puzzle puzzle);

    /**
     * In the data store change the solution of a puzzle.
     *
     * @param puzzle      The puzzle
     * @param newSolution The new solution
     * @return If the puzzle exists and was updated successfully, the puzzle with the new solution is returned otherwise a null value is returned
     */
    Puzzle updatePuzzleSolution(Puzzle puzzle, String newSolution);
}
