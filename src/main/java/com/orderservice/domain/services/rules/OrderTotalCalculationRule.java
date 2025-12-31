package com.orderservice.domain.services.rules;

import com.orderservice.domain.model.Order;

import com.orderservice.domain.services.contexts.TaxCalculationContext;
import com.orderservice.domain.services.specifications.CalculationRule;
import com.orderservice.domain.services.strategies.OrderTaxCalculationStrategy;

public class OrderTotalCalculationRule implements CalculationRule<TaxCalculationContext> {

    private final OrderTaxCalculationStrategy strategy;

    public OrderTotalCalculationRule(OrderTaxCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public Order calculate(Order order, TaxCalculationContext context) {
        double taxAmount = strategy.resolveTax(order, context);
        return order.withTotal(order.getTotal() + taxAmount);
    }
}
