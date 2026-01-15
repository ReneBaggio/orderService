package com.orders.domain.services.rules.invariants;

import com.orders.domain.models.Order;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.specifications.InvariantRule;

public final class OrderMustHaveAtLeastOneItemRule implements InvariantRule {
    @Override
    public RuleResult validate(Order order) {
        return (order.getItems() == null || order.getItems().isEmpty())
                ? RuleResult.error("ORDER_EMPTY", "Order must contain at least one item.")
                : RuleResult.ok();
    }
}
