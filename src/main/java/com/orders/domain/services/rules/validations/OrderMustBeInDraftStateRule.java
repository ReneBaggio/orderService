package com.orders.domain.services.rules.validations;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderState;
import com.orders.domain.services.contexts.OrderValidationContext;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.specifications.ValidationRule;

public final class OrderMustBeInDraftStateRule implements ValidationRule<OrderValidationContext> {
    @Override
    public RuleResult validate(Order order, OrderValidationContext context) {
        return order.getState() == OrderState.DRAFT
                ? RuleResult.ok()
                : RuleResult.error("STATE_INVALID", "Order must be in DRAFT state for creation.");
    }
}
