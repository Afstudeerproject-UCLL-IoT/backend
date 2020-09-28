package core.usecases.device.command;

import core.domain.Device;
import core.domain.Puzzle;
import core.exceptions.subscribe.CannotSubscribeToItselfException;
import core.exceptions.subscribe.DeviceCannotSubscribeToPuzzleException;
import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class SubscribeToPuzzleCommand {

    public static ImmutablePair<Device, Puzzle> handle(Device subscriber,
                                                       Puzzle puzzle,
                                                       DeviceRepository deviceRepository,
                                                       PuzzleRepository puzzleRepository){
        // null checks
        if(subscriber == null || puzzle == null)
            throw new IllegalArgumentException("Device or puzzle cannot be null");

        // check if it's not going to subscribe to itself
        if(subscriber.getPuzzle().equals(puzzle))
            throw new CannotSubscribeToItselfException();

        // check if the puzzle and device exist
        if(!deviceRepository.exists(subscriber) || !puzzleRepository.exists(puzzle))
            throw new DeviceCannotSubscribeToPuzzleException();

        // TODO check if they are not already subscribed

        // TODO a device is subscribed for a puzzle for a specific game
        // subscribe to the puzzle
        puzzleRepository.addSubscription(subscriber, puzzle);

        return ImmutablePair.of(subscriber, puzzle);
    }
}
