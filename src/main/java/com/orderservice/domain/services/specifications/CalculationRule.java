package com.orderservice.domain.services.specifications;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.services.contexts.RuleContext;

public interface CalculationRule<T extends RuleContext> {
    public Order calculate(Order order, T context);
}
