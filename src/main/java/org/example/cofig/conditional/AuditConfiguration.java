package org.example.cofig.conditional;


import org.example.Main;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = Main.class)
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String>auditorAware(){
        //SecurityContext.getCurrentUser().getEmail()
        return () -> Optional.of("moroz");
    }
}
