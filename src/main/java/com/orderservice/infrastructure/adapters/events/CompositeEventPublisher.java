package com.orderservice.infrastructure.adapters.events;

import com.orderservice.domain.events.DomainEvent;
import com.orderservice.domain.ports.EventPublisher;

import java.util.List;

public class CompositeEventPublisher implements EventPublisher {

    private final List<EventPublisher> publishers;

    public CompositeEventPublisher(List<EventPublisher> publishers)
    {
        this.publishers = publishers;
    }

    @Override
    public void publish(DomainEvent event) {
        for(EventPublisher publisher : publishers)
            publisher.publish(event);
    }
}
