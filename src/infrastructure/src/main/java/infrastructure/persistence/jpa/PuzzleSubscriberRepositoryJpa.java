package infrastructure.persistence.jpa;

import infrastructure.persistence.entities.PuzzleSubscriberEntity;
import infrastructure.persistence.entities.PuzzleSubscriberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleSubscriberRepositoryJpa extends JpaRepository<PuzzleSubscriberEntity, PuzzleSubscriberId> {
}
