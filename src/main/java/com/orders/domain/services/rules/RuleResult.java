package com.orders.domain.services.rules;

public record RuleResult(boolean valid, String errorCode, String message) {
    public static RuleResult ok() {
        return new RuleResult(true, null, null);
    }
    public static RuleResult error(String errorCode, String message) {
        return new RuleResult(false, errorCode, message);
    }
}
