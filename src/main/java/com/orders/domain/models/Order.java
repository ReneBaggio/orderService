package com.orders.domain.models;

import com.orders.domain.events.DomainEvent;
import com.orders.domain.services.policies.InvariantCompositeRule;
import com.orders.domain.services.rules.invariants.OrderItemQuantityMustBePositiveRule;
import com.orders.domain.services.rules.invariants.OrderMustHaveAtLeastOneItemRule;
import com.orders.domain.services.rules.invariants.OrderMustHaveCurrencyRule;
import com.orders.domain.services.specifications.InvariantRule;
import com.orders.domain.violations.DomainInvariantViolation;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Order {
    @Getter
    private final UUID id;
    @Getter
    private final OrderState state;
    @Getter
    private final String customerId;
    @Getter
    private final Currency currency;
    @Getter
    private final List<OrderItem> items;
    @Getter
    private final int numOfItems;
    @Getter
    private final double subTotal;
    @Getter
    private final double tax;
    @Getter
    private final double total;

    private final List<DomainEvent> domainEvents = new CopyOnWriteArrayList<>();

    private Order(String customerId, Currency currency, List<OrderItem> items) {
        this.id = UUID.randomUUID();
        this.state = OrderState.DRAFT;
        this.customerId = customerId;
        this.currency = currency;
        this.items = items;
        this.numOfItems = 0;
        this.subTotal = 0;
        this.tax = 0;
        this.total = 0;
    }

    private Order(UUID id, OrderState state, String customerId, Currency currency, List<OrderItem> items, int numOfItems, double subTotal, double tax, double total) {
        this.id = id;
        this.state = state;
        this.customerId = customerId;
        this.currency = currency;
        this.items = items;
        this.numOfItems = numOfItems;
        this.subTotal = subTotal;
        this.tax = tax;
        this.total = total;
    }

    public static Order create(String customerId, Currency currency, List<OrderItem> items) {
        Order order = new Order(customerId, currency, items);
        order.ensureInvariants();
        return order;
    }

    public static Order restore(UUID id, OrderState state, String customerId, Currency currency, List<OrderItem> items, int numOfItems, double subTotal, double tax, double total) {
        Order order = new Order(id, state, customerId, currency, items, numOfItems, subTotal, tax, total);
        order.ensureInvariants();
        return order;
    }

    private void ensureInvariants() {
        Queue<InvariantRule> invariants = new ArrayDeque<>();
        invariants.add(new OrderMustHaveAtLeastOneItemRule());
        invariants.add(new OrderItemQuantityMustBePositiveRule());
        invariants.add(new OrderMustHaveCurrencyRule());

        var result = new InvariantCompositeRule(invariants)
                .validate(this);

        if(!result.valid())
            throw new DomainInvariantViolation(result);
    }

    public Order withSubTotal(double subTotal) {
        return new Order(id, state, customerId, currency, items, numOfItems, subTotal, tax, total);
    }

    public Order withNumOfItems(int numOfItems) {
        return new Order(id, state, customerId, currency, items, numOfItems, subTotal, tax, total);
    }

    public Order withTax(double tax) {
        return new Order(id, state, customerId, currency, items, numOfItems, subTotal, tax, total);
    }

    public Order withTotal(double total) {
        return new Order(id, state, customerId, currency, items, numOfItems, subTotal, tax, total);
    }

    public void pushDomainEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> copy = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return copy;
    }
}