package com.orders.domain.services.specifications;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.RuleContext;
import com.orders.domain.services.rules.RuleResult;

public interface ValidationRule<T extends RuleContext> {
    RuleResult validate(Order order, T context);
}
