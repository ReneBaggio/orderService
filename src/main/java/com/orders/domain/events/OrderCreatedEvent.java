package com.orders.domain.events;

import java.util.UUID;

public record OrderCreatedEvent(UUID orderId) implements DomainEvent {

}
