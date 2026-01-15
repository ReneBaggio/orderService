package com.orders.domain.ports;

import com.orders.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
