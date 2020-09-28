package infrastructure.persistence.implementations;

import core.domain.Device;
import core.domain.Puzzle;
import core.interfaces.repositories.PuzzleRepository;
import infrastructure.persistence.entities.PuzzleSubscriberEntity;
import infrastructure.persistence.jpa.DeviceRepositoryJpa;
import infrastructure.persistence.jpa.PuzzleRepositoryJpa;
import infrastructure.persistence.jpa.PuzzleSubscriberRepositoryJpa;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class PuzzleRepositoryImpl implements PuzzleRepository {

    private final PuzzleRepositoryJpa puzzleRepository;
    private final PuzzleSubscriberRepositoryJpa puzzleSubscriberRepositoryJpa;

    public PuzzleRepositoryImpl(PuzzleRepositoryJpa puzzleRepository, PuzzleSubscriberRepositoryJpa puzzleSubscriberRepositoryJpa) {
        this.puzzleRepository = puzzleRepository;
        this.puzzleSubscriberRepositoryJpa = puzzleSubscriberRepositoryJpa;
    }

    @Override
    public void addSubscription(Device subscriber, Puzzle puzzle) {
        var optionalPuzzleEntity = puzzleRepository.findById(puzzle.getName());
        var optionalSubscriber = puzzleRepository.findById(subscriber.getPuzzle().getName());

        if(optionalPuzzleEntity.isPresent() && optionalSubscriber.isPresent()){
            var puzzleEntity = optionalPuzzleEntity.get();
            var subscriberEntity = optionalSubscriber.get().getDevice();

            subscriberEntity.addSubscription(puzzleEntity);
        }
    }

    @Override
    public List<Device> getSubscriptions(Puzzle puzzle) {
        return puzzleSubscriberRepositoryJpa.findAll()
                .stream()
                .filter(entity -> entity.getPuzzle().getName().equals(puzzle.getName()))
                .map(entity -> new Device.Builder()
                        .withId(entity.getId().getDeviceId())
                        .fromDeviceName(String.format("%s-%s", entity.getDevice().getType(), entity.getDevice().getPuzzle().getName()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(Puzzle puzzle) {
        return puzzleRepository.existsById(puzzle.getName());
    }
}
