package org.example.cofig;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

//@Conditional(JpaConditional.class)
@Configuration
@Slf4j
public class JpaConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "bd")
//    public DatabaseProperties databaseProperties(){
//        return new DatabaseProperties();
//
//    }

    @PostConstruct
    void init(){
        log.info("JapConfiguration init method");
    }
}
