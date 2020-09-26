package core.interfaces.repositories;

import core.domain.Device;
import core.domain.Puzzle;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;


public interface PuzzleRepository extends GenericRepository<Puzzle> {

    // as a device subscribe myself to a puzzle
    ImmutablePair<Device, Puzzle> addSubscription(Device subscriber, Puzzle puzzle);

    // get all the devices that are subscribed to a puzzle
    List<Device> getSubscriptions(Puzzle puzzle);
}
