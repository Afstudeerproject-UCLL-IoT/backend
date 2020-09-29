package infrastructure.di;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("infrastructure.persistence")
@EntityScan("infrastructure.persistence")
public class PersistenceConfiguration {

}
