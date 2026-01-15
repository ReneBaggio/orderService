package com.orders.domain.violations;

import com.orders.domain.services.rules.RuleResult;
import lombok.Getter;

public class BusinessRuleViolation extends RuntimeException {
    @Getter
    private final RuleResult ruleResult;

    public BusinessRuleViolation(RuleResult ruleResult) {
        super(ruleResult.message());
        this.ruleResult = ruleResult;
    }
}
