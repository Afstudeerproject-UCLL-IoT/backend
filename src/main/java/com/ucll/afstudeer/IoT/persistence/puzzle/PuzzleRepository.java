package com.ucll.afstudeer.IoT.persistence.puzzle;


import com.ucll.afstudeer.IoT.domain.Device;
import com.ucll.afstudeer.IoT.domain.Puzzle;
import com.ucll.afstudeer.IoT.persistence.GenericRepository;

import java.util.List;

public interface PuzzleRepository extends GenericRepository<Puzzle, String> {
    // get all the devices that are subscribed to a puzzle
    List<Device> getSubscriptions(Puzzle puzzle);
}
