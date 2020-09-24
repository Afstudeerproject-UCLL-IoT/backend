package core.usecases.device.command;

import core.domain.device.Device;
import core.domain.puzzle.Puzzle;
import core.exceptions.subscribe.CannotSubscribeToItselfException;
import core.exceptions.subscribe.DeviceCannotSubscribeToPuzzleException;
import core.interfaces.DeviceRepository;
import core.interfaces.PuzzleRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class SubscribeToPuzzleCommand {

    public static ImmutablePair<Device, Puzzle> handle(String subscriberDeviceName,
                                                       String puzzleName,
                                                       DeviceRepository deviceRepository,
                                                       PuzzleRepository puzzleRepository){
        // create the device and puzzle
        var device = Device.instance(subscriberDeviceName);
        var puzzle = Puzzle.instance(puzzleName, "");

        // check if it's not going to subscribe to itself
        if(device.getPuzzle().getName().contains(puzzleName))
            throw new CannotSubscribeToItselfException();

        // check if the puzzle and device exist
        if(!deviceRepository.exists(device) || !puzzleRepository.exists(puzzle))
            throw new DeviceCannotSubscribeToPuzzleException();

        // TODO a device is subscribed for a puzzle for a specific game
        // subscribe to the puzzle
        puzzleRepository.addSubscription(device, puzzle);

        return ImmutablePair.of(device, puzzle);
    }
}
