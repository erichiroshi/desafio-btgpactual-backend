package com.erichiroshi.desafiobtgpactualbackend.application.input;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;

import java.util.Set;
import java.util.stream.Collectors;

public record OrderInput(
        Long orderId,
        long customerId,
        Set<OrderItemInput> itemInputs) {

    public Order toDomain() {
        return new Order(orderId, customerId, orderItemsSet());
    }

    private Set<OrderItem> orderItemsSet() {
        return itemInputs.stream()
                .map(OrderItemInput::toDomain)
                .collect(Collectors.toSet());
    }

}
