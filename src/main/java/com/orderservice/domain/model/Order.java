package com.orderservice.domain.model;

import com.orderservice.domain.events.DomainEvent;
import com.orderservice.domain.events.OrderCreatedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Order {
    private final UUID id;
    private final String customerId;
    private final List<OrderItem> items;
    private double total;

    private final List<DomainEvent> domainEvents = new CopyOnWriteArrayList<>();

    private Order(String customerId, List<OrderItem> items) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = items;
        this.total = calculateTotal();

        this.domainEvents.add(new OrderCreatedEvent(this.id, this.customerId, this.total));
    }

    private Order(UUID id, String customerId, List<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.total = calculateTotal();
    }

    public static Order create(String customerId, List<OrderItem> items) {
        if(customerId.isBlank() || customerId == null)
            throw new IllegalArgumentException("Customer id must be a valid value");
        if(items == null || items.isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");

        return new Order(customerId, items);
    }

    public static Order restore(UUID id, String customerId, List<OrderItem> items) {
        if(id == null)
            throw new IllegalArgumentException("Order id is invalid");
        if(customerId.isBlank() || customerId == null)
            throw new IllegalArgumentException("Customer id must be a valid value");
        if(items == null || items.isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");

        return new Order(id, customerId, items);
    }

    private double calculateTotal() {
        return items.stream().mapToDouble(OrderItem::getPrice).sum();
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public double getTotal() {
        return total;
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> copy = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return copy;
    }
}