package getithere.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendConfig {
    
    @Bean
    public MongoConnection mongoConnection(){
        return new MongoConnection();
    }
}
