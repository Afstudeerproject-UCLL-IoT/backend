package infrastructure.persistence.implementations;

import core.domain.Device;
import core.interfaces.repositories.DeviceRepository;
import infrastructure.persistence.entities.DeviceEntity;
import infrastructure.persistence.jpa.DeviceRepositoryJpa;
import infrastructure.persistence.jpa.PuzzleRepositoryJpa;

public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryJpa deviceRepository;
    private final PuzzleRepositoryJpa puzzleRepository;

    public DeviceRepositoryImpl(DeviceRepositoryJpa deviceRepository, PuzzleRepositoryJpa puzzleRepository) {
        this.deviceRepository = deviceRepository;
        this.puzzleRepository = puzzleRepository;
    }

    @Override
    public Device add(Device device) {
        var entity = DeviceEntity.from(device);
        deviceRepository.saveAndFlush(entity);

        return device;
    }

    @Override
    public boolean exists(Device device) {
        return puzzleRepository.existsById(device.getPuzzle().getName());
    }

    @Override
    public boolean remove(Device device) {
        var puzzle = puzzleRepository.findById(device.getPuzzle().getName());

        if(puzzle.isPresent()){
            deviceRepository.delete(puzzle.get().getDevice());
            return true;
        }
        return false;
    }
}
