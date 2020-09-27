package web.configuration;

import core.interfaces.repositories.DeviceRepository;
import core.interfaces.repositories.PuzzleRepository;
import core.usecases.device.DeviceUseCases;
import core.usecases.device.DeviceUseCasesImpl;
import infrastructure.persistence.PersistenceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfiguration.class)
public class ApplicationConfiguration {

    @Bean
    public DeviceUseCases deviceUseCases(PuzzleRepository puzzleRepository, DeviceRepository deviceRepository){
        return new DeviceUseCasesImpl(deviceRepository, puzzleRepository, null);
    }

}
