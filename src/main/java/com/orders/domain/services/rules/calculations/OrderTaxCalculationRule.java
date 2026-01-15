package com.orders.domain.services.rules.calculations;

import com.orders.domain.ports.OrderTaxStrategyResolver;
import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.specifications.CalculationRule;

public class OrderTaxCalculationRule implements CalculationRule<OrderCalculationContext> {

    private final OrderTaxStrategyResolver resolver;

    public OrderTaxCalculationRule(OrderTaxStrategyResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public Order calculate(Order order, OrderCalculationContext context) {
        var strategy = resolver.resolve(context.countryCode());
        double taxAmount = strategy.calculateTaxAmount(order, context);
        return order.withTax(taxAmount);
    }
}
