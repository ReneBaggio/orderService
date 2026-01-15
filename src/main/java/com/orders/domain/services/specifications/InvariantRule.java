package com.orders.domain.services.specifications;

import com.orders.domain.models.Order;
import com.orders.domain.services.rules.RuleResult;

public interface InvariantRule {
    RuleResult validate(Order order);
}
