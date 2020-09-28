package core.interfaces.repositories;

import core.domain.Device;
import core.domain.Puzzle;

import java.util.List;


public interface PuzzleRepository extends GenericRepository<Puzzle> {

    // as a device subscribe myself to a puzzle
    void addSubscription(Device subscriber, Puzzle puzzle);

    // get all the devices that are subscribed to a puzzle
    List<Device> getSubscriptions(Puzzle puzzle);
}
