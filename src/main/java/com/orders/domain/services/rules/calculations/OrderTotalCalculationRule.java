package com.orders.domain.services.rules.calculations;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.specifications.CalculationRule;

public class OrderTotalCalculationRule implements CalculationRule<OrderCalculationContext> {
    @Override
    public Order calculate(Order order, OrderCalculationContext context) {
        return order.withTotal(order.getSubTotal() +  order.getTax());
    }
}
