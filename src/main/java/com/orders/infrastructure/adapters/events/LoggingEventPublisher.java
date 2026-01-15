package com.orders.infrastructure.adapters.events;

import com.orders.domain.events.DomainEvent;
import com.orders.domain.ports.EventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventPublisher implements EventPublisher {

    @Override
    public void publish(DomainEvent event) {
        System.out.println("[EVENT PUBLISHED] " + event.getClass().getSimpleName() + " -> " + event);
    }
}
