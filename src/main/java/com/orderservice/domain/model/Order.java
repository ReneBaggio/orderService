package com.orderservice.domain.model;

import com.orderservice.domain.events.DomainEvent;
import com.orderservice.domain.events.OrderCreatedEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Order {
    @Getter
    private final UUID id;
    @Getter
    private final String customerId;
    @Getter
    private final List<OrderItem> items;
    @Getter
    private final double subTotal;
    @Getter
    private double total;

    private final List<DomainEvent> domainEvents = new CopyOnWriteArrayList<>();

    private Order(String customerId, List<OrderItem> items) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = items;
        this.subTotal = calculateSubTotal();

        this.domainEvents.add(new OrderCreatedEvent(this.id, this.customerId, this.subTotal));
    }

    private Order(UUID id, String customerId, List<OrderItem> items, double total) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.subTotal = calculateSubTotal();
        this.total = total;
    }

    public static Order create(String customerId, List<OrderItem> items) {
        if(customerId.isBlank())
            throw new IllegalArgumentException("Customer id must be a valid value");
        if(items == null || items.isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");

        return new Order(customerId, items);
    }

    public static Order restore(UUID id, String customerId, List<OrderItem> items, double total) {
        if(id == null)
            throw new IllegalArgumentException("Order id is invalid");
        if(customerId.isBlank())
            throw new IllegalArgumentException("Customer id must be a valid value");
        if(items == null || items.isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");
        if(total < 0)
            throw new IllegalArgumentException("Total must be bigger or equal than subtotal");

        return new Order(id, customerId, items, total);
    }

    private double calculateSubTotal() {
        return items.stream().mapToDouble(OrderItem::getPrice).sum();
    }

    public Order withTotal(double total) {
        return restore(this.id, this.customerId, this.items, total);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> copy = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return copy;
    }
}