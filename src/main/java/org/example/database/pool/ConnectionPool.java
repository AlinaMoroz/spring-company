package org.example.database.pool;



import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("pool1")
//@RequiredArgsConstructor
@Slf4j
public class ConnectionPool {
//
//    @Value("${bd.username}")                                                                                                                            @Value("${bd.name}")
    private final String username;
//
//
//    @Value("${bd.pool.size}")
    private final Integer poolSize;

    @Autowired
    public ConnectionPool( @Value("${db.username}") String username,@Value("${db.pool.size}") Integer poolSize) {
        this.username = username;
        this.poolSize = poolSize;
    }


    @PostConstruct
    private void init(){
        log.info("Init method " + username + " " + poolSize);


    }
}
