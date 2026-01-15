package com.orders.domain.services.rules.calculations;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.specifications.CalculationRule;

public final class OrderSubtotalCalculationRule implements CalculationRule<OrderCalculationContext> {
    @Override
    public Order calculate(Order order, OrderCalculationContext context) {
        double subTotal = 0;
        for (OrderItem item : order.getItems()) {
            subTotal += item.getUnitPrice() * item.getQuantity();
        }
        return order.withSubTotal(subTotal);
    }
}
