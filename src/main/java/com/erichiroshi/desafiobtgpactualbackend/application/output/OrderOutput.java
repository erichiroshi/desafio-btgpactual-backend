package com.erichiroshi.desafiobtgpactualbackend.application.output;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderOutput(
        long orderId,
        long customerId,
        Set<OrderItemOutput> itemOutputs,
        BigDecimal total
) {

    public static OrderOutput fromDomain(Order order) {
        return new OrderOutput(order.getOrderId(), order.getCustomerId(), orderItemOutputsSet(order), order.getTotal());
    }

    private static Set<OrderItemOutput> orderItemOutputsSet(Order order) {
        return order.getItems().stream()
                .map(OrderItemOutput::fromDomain)
                .collect(Collectors.toSet());
    }
}
