package infrastructure.persistence.implementations;

import core.domain.Device;
import core.domain.Puzzle;
import core.interfaces.repositories.PuzzleRepository;
import infrastructure.persistence.entities.DeviceEntity;
import infrastructure.persistence.entities.PuzzleEntity;
import infrastructure.persistence.entities.PuzzleSubscriberEntity;
import infrastructure.persistence.jpa.PuzzleRepositoryJpa;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PuzzleRepositoryImpl implements PuzzleRepository {

    private final PuzzleRepositoryJpa puzzleRepository;

    public PuzzleRepositoryImpl(PuzzleRepositoryJpa puzzleRepository){
        this.puzzleRepository = puzzleRepository;
    }

    @Override
    public ImmutablePair<Device, Puzzle> addSubscription(Device subscriber, Puzzle puzzle) {
        var optionalPuzzleEntity = puzzleRepository.findById(puzzle.getName());

        if(optionalPuzzleEntity.isPresent()){
            var puzzleEntity = optionalPuzzleEntity.get();
            var deviceEntity = puzzleEntity.getDevice();

            var puzzleSubscriberEntity = new PuzzleSubscriberEntity();
            puzzleSubscriberEntity.setDevice(deviceEntity);
            puzzleSubscriberEntity.setPuzzle(puzzleEntity);

            puzzleEntity.getSubscribers().add(puzzleSubscriberEntity);

            puzzleRepository.saveAndFlush(puzzleEntity);

            return ImmutablePair.of(subscriber, puzzle);
        }

        // TODO exception?
        return null;
    }

    @Override
    public List<Device> getSubscriptions(Puzzle puzzle) {
        var optionalPuzzleEntity = puzzleRepository.findById(puzzle.getName());

        if(optionalPuzzleEntity.isPresent()){
            var puzzleEntity = optionalPuzzleEntity.get();

            return puzzleEntity.getSubscribers()
                    .stream()
                    .map(entity -> Device.instance(String.format("%s-%s", entity.getDevice().getType().toString(), entity.getPuzzle().getName())))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public Puzzle add(Puzzle puzzle) {
        var entity = PuzzleEntity.from(puzzle);
        puzzleRepository.saveAndFlush(entity);

        return puzzle;
    }

    @Override
    public boolean exists(Puzzle puzzle) {
        return puzzleRepository.existsById(puzzle.getName());
    }

    @Override
    public boolean remove(Puzzle puzzle) {
        var optionalPuzzleEntity = puzzleRepository.findById(puzzle.getName());

        if(optionalPuzzleEntity.isPresent()){
            var puzzleEntity = optionalPuzzleEntity.get();
            puzzleRepository.delete(puzzleEntity);

            return true;
        }

        return false;
    }
}
