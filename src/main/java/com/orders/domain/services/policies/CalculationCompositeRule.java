package com.orders.domain.services.policies;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.RuleContext;
import com.orders.domain.services.specifications.CalculationRule;

import java.util.Queue;

public final class CalculationCompositeRule<T extends RuleContext> implements CalculationRule<T> {

    private final Queue<CalculationRule<T>> rules;

    public CalculationCompositeRule(Queue<CalculationRule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public Order calculate(Order order, T context) {
        Order current = order;
        for(CalculationRule<T> rule : rules) {
            current = rule.calculate(current, context);
        }
        return current;
    }
}
