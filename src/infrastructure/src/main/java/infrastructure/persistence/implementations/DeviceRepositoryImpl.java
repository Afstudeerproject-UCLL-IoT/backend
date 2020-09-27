package infrastructure.persistence.implementations;

import core.domain.Device;
import core.interfaces.repositories.DeviceRepository;
import infrastructure.persistence.entities.DeviceEntity;
import infrastructure.persistence.entities.PuzzleEntity;
import infrastructure.persistence.jpa.DeviceRepositoryJpa;
import infrastructure.persistence.jpa.PuzzleRepositoryJpa;

import javax.transaction.Transactional;

@Transactional
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryJpa deviceRepository;
    private final PuzzleRepositoryJpa puzzleRepository;

    public DeviceRepositoryImpl(DeviceRepositoryJpa deviceRepository, PuzzleRepositoryJpa puzzleRepository) {
        this.deviceRepository = deviceRepository;
        this.puzzleRepository = puzzleRepository;
    }

    @Override
    public boolean exists(Device device) {
        return puzzleRepository.existsById(device.getPuzzle().getName());
    }

    @Override
    public Device addDeviceWithPuzzle(Device device) {
        var deviceEntity = DeviceEntity.from(device);
        var puzzleEntity = PuzzleEntity.from(device.getPuzzle());

        deviceEntity.setPuzzle(puzzleEntity);
        puzzleEntity.setDevice(deviceEntity);

        deviceRepository.saveAndFlush(deviceEntity);

        return device;
    }
}
