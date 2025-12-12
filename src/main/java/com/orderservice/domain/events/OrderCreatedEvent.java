package com.orderservice.domain.events;

import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, String customerId, double total) implements DomainEvent {

}
