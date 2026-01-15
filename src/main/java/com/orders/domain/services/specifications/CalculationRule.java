package com.orders.domain.services.specifications;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.RuleContext;

public interface CalculationRule<T extends RuleContext> {
    public Order calculate(Order order, T context);
}
