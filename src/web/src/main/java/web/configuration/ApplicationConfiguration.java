package web.configuration;

import infrastructure.persistence.PersistenceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfiguration.class)
public class ApplicationConfiguration {

}
