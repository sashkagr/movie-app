package by.hribouskaya.app.movie.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HibernateProperties.class)
@ConfigurationProperties(prefix = "app.hibernate")
@Getter
@Setter
public class HibernateProperties {

    private String url;
    private String username;
    private String password;
}
