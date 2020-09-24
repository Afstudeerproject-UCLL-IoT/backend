package core.interfaces;

import core.domain.device.Device;
import core.domain.puzzle.Puzzle;
import org.apache.commons.lang3.tuple.ImmutablePair;


public interface PuzzleRepository extends GenericRepository<Puzzle>{
    ImmutablePair<Device, Device> addSubscription(Device subscriber, Puzzle puzzle);
}
