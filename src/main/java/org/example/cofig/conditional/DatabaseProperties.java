package org.example.cofig.conditional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;


import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "db")
public record DatabaseProperties(String username,
                                 String password,
                                 String driver,
                                 String url,
                                 String hosts,
                                 PoolProperties pool,
                                 List<PoolProperties> pools,
                                 Map<String, Object> properties) {

    public static record PoolProperties(Integer size,
                                        Integer timeout) {
    }
}
