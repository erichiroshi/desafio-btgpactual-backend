package com.erichiroshi.desafiobtgpactualbackend.application.output;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;

import java.math.BigDecimal;

public record OrderItemOutput(
        String product,
        int quantity,
        BigDecimal price,
        BigDecimal total
) {

    public static OrderItemOutput fromDomain(OrderItem orderItem) {
        return new OrderItemOutput(
                orderItem.getProduct(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getTotal());
    }
}
