package com.orderservice.domain.services.specifications;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.services.contexts.RuleContext;

public interface ValidationRule<T extends RuleContext> {
    boolean validate(Order order, T context);
}
