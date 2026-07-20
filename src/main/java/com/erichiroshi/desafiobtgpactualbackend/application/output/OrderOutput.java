package com.erichiroshi.desafiobtgpactualbackend.application.output;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;

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
        return new OrderOutput(
                order.getOrderId(),
                order.getCustomerId(),
                orderItemOutputsSet(order.getItems()),
                order.getTotal());
    }

    private static Set<OrderItemOutput> orderItemOutputsSet(Set<OrderItem> set) {
        return set.stream()
                .map(OrderItemOutput::fromDomain)
                .collect(Collectors.toSet());
    }
}
