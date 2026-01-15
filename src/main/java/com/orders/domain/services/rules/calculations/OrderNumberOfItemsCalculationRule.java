package com.orders.domain.services.rules.calculations;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.specifications.CalculationRule;

public class OrderNumberOfItemsCalculationRule implements CalculationRule<OrderCalculationContext> {
    @Override
    public Order calculate(Order order, OrderCalculationContext context) {
        int numberOfItems = 0;
        for (OrderItem item : order.getItems()) {
            numberOfItems += item.getQuantity();
        }
        return order.withNumOfItems(numberOfItems);
    }
}
