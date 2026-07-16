package com.erichiroshi.desafiobtgpactualbackend.application.input;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;

import java.math.BigDecimal;

public record OrderItemInput(
        String product,
        int quantity,
        BigDecimal price
) {

    public OrderItem toDomain() {
        return new OrderItem(product, quantity, price);
    }
}
