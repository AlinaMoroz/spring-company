package org.example.listner.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityListener {

    @EventListener
    public void acceptEntity(EntityEvent entityEvent){
        log.info("Entity: " + entityEvent);
    }
}
