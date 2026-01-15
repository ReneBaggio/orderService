package com.orders.domain.vo;

import lombok.Getter;

import java.util.Currency;

public class Money {
    @Getter
    public double amount;
    @Getter
    public Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
