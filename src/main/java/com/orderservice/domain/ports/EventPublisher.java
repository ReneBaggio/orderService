package com.orderservice.domain.ports;

import com.orderservice.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
