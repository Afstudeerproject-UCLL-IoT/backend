package core.interfaces.repositories;

import core.domain.Device;
import core.domain.Puzzle;

import java.util.List;


public interface PuzzleRepository extends GenericRepository<Puzzle> {
    // get all the devices that are subscribed to a puzzle
    List<Device> getSubscriptions(Puzzle puzzle);
}
