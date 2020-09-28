package infrastructure.di;

import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;
import infrastructure.persistence.implementations.DeviceRepositoryImpl;
import infrastructure.persistence.implementations.PuzzleRepositoryImpl;
import infrastructure.persistence.jpa.DeviceRepositoryJpa;
import infrastructure.persistence.jpa.PuzzleRepositoryJpa;
import infrastructure.persistence.jpa.PuzzleSubscriberRepositoryJpa;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("infrastructure.persistence")
@EntityScan("infrastructure.persistence")
public class PersistenceConfiguration {

    @Bean
    public DeviceRepository deviceRepository(DeviceRepositoryJpa deviceRepositoryJpa, PuzzleRepositoryJpa puzzleRepositoryJpa){
        return new DeviceRepositoryImpl(deviceRepositoryJpa, puzzleRepositoryJpa);
    }

    @Bean
    public PuzzleRepository puzzleRepository(PuzzleSubscriberRepositoryJpa puzzleSubscriberRepositoryJpa, PuzzleRepositoryJpa puzzleRepositoryJpa){
        return new PuzzleRepositoryImpl(puzzleRepositoryJpa,puzzleSubscriberRepositoryJpa);
    }
}
