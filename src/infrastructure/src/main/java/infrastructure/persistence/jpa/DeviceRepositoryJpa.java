package infrastructure.persistence.jpa;

import infrastructure.persistence.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepositoryJpa extends JpaRepository<DeviceEntity, Long> {
}
