package com.orders.domain.models;

import lombok.Getter;

public class OrderItem {
    @Getter
    private final String productId;
    @Getter
    private final int quantity;
    @Getter
    private final double unitPrice;

    public OrderItem(String productId, int quantity, double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
