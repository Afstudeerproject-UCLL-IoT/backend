package infrastructure.persistence;

import core.interfaces.MessageRepository;
import core.usecases.greeter.GreeterActions;
import infrastructure.persistence.implementations.MessageRepositoryImpl;
import infrastructure.persistence.repositories.MessageRepositoryJpa;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("infrastructure.persistence.*")
@EnableJpaRepositories("infrastructure.persistence.*")
@EntityScan("infrastructure.persistence.*")
public class PersistenceConfiguration {

    @Bean
    public MessageRepository testRepository(MessageRepositoryJpa messageRepositoryJpa){
        return new MessageRepositoryImpl(messageRepositoryJpa);
    }

    @Bean
    public GreeterActions testActions(MessageRepository messageRepository){
        return new GreeterActions(messageRepository);
    }
}
