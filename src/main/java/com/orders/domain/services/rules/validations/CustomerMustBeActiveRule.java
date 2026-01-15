package com.orders.domain.services.rules.validations;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.OrderValidationContext;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.specifications.ValidationRule;

public final class CustomerMustBeActiveRule implements ValidationRule<OrderValidationContext> {
    @Override
    public RuleResult validate(Order order, OrderValidationContext context) {
        return context.customerActive()
                ? RuleResult.ok()
                : RuleResult.error("CUSTOMER_INACTIVE", "Customer must be active to place an order.");
    }
}
