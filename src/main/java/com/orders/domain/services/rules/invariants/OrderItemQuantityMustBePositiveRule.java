package com.orders.domain.services.rules.invariants;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.specifications.InvariantRule;

public final class OrderItemQuantityMustBePositiveRule implements InvariantRule {
    @Override
    public RuleResult validate(Order order) {
        for(OrderItem item : order.getItems()) {
            if(item.getQuantity() <= 0) {
                return RuleResult.error("QTY_INVALID", "Order item quantity must be > 0.");
            }
        }
        return RuleResult.ok();
    }
}
