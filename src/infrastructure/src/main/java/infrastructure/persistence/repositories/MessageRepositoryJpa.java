package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepositoryJpa extends JpaRepository<MessageEntity, Long> {

}
