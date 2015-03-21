package name.msutherland.hackathon.mvc;


import name.msutherland.hackathon.backend.MongoConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="name.msutherland.hackathon")
public class WebConfig {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/api/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    @Bean
    public ApiActions apiActions(MongoConnection mongoConnection){
        return new ApiActions(mongoConnection);
        
    }

}
