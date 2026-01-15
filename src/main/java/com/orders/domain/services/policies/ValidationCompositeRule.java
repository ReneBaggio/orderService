package com.orders.domain.services.policies;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.RuleContext;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.specifications.ValidationRule;

import java.util.Queue;

public final class ValidationCompositeRule<T extends RuleContext> implements ValidationRule<T> {

    private final Queue<ValidationRule<T>> rules;

    public ValidationCompositeRule(Queue<ValidationRule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public RuleResult validate(Order order, T context) {
        for(ValidationRule<T> rule : rules) {
            RuleResult result = rule.validate(order, context);
            if(!result.valid()) return result;
        }
        return RuleResult.ok();
    }
}
