package com.orders.domain.services.rules.invariants;

import com.orders.domain.models.Order;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.specifications.InvariantRule;

public final class OrderMustHaveCurrencyRule implements InvariantRule {

    @Override
    public RuleResult validate(Order order) {
        return order.getCurrency() == null
                ? RuleResult.error("CURRENCY_MISSING", "Order currency is required.")
                : RuleResult.ok();
    }
}
