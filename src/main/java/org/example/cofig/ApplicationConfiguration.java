package org.example.cofig;

import org.example.database.pool.ConnectionPool;
import org.example.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ConnectionPool pool2(@Value("${db.username}") String name, @Value("${db.pool.size}") Integer size){
        return new ConnectionPool(name, size);
    }


}
