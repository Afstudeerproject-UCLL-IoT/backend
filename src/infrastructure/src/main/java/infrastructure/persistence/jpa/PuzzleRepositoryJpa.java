package infrastructure.persistence.jpa;

import infrastructure.persistence.entities.PuzzleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleRepositoryJpa extends JpaRepository<PuzzleEntity, String> {
}
