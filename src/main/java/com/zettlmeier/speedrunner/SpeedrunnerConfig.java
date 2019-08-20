package com.zettlmeier.speedrunner;

import com.zettlmeier.speedrunner.entities.service.RestResponseEntityBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeedrunnerConfig {
    @Bean
    public RestResponseEntityBuilder restResponseEntityBuilder() { return new RestResponseEntityBuilder(); }
}
