package com.orderservice.domain.services.policies;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.services.contexts.RuleContext;
import com.orderservice.domain.services.specifications.ValidationRule;

import java.util.Queue;

public class ValidationCompositeRule<T extends RuleContext> implements ValidationRule<T> {

    private final Queue<ValidationRule<T>> rules;

    public ValidationCompositeRule(Queue<ValidationRule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public boolean validate(Order order, T context) {
        for(ValidationRule<T> rule : rules) {
            if(rule.validate(order, context)) {
                return false;
            }
        }
        return true;
    }
}
