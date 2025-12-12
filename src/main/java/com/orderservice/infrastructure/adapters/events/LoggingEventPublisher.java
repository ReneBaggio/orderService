package com.orderservice.infrastructure.adapters.events;

import com.orderservice.domain.events.DomainEvent;
import com.orderservice.domain.ports.EventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventPublisher implements EventPublisher {

    @Override
    public void publish(DomainEvent event) {
        System.out.println("[EVENT PUBLISHED] " + event.getClass().getSimpleName() + " -> " + event);
    }
}
