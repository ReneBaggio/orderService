package com.orders.domain.services.policies;

import com.orders.domain.models.Order;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.specifications.InvariantRule;

import java.util.Queue;

public final class InvariantCompositeRule implements InvariantRule {

    private final Queue<InvariantRule> rules;

    public InvariantCompositeRule(Queue<InvariantRule> rules) {
        this.rules = rules;
    }

    @Override
    public RuleResult validate(Order order) {
        for (InvariantRule rule : rules) {
            RuleResult result = rule.validate(order);
            //if a rule fails, short circuit the policy
            if(!result.valid()) return result;
        }
        return RuleResult.ok();
    }
}
