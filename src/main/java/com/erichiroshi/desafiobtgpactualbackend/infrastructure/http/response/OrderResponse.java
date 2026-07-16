package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderItemOutput;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderResponse(
        long orderId,
        long customerId,
        BigDecimal total,
        Set<OrderItemResponse> items
) {

    public static OrderResponse fromOutput(OrderOutput orderOutput) {
        return new OrderResponse(
                orderOutput.orderId(),
                orderOutput.customerId(),
                orderOutput.total(),
                orderItemsResponseSet(orderOutput.itemOutputs())
        );
    }

    private static Set<OrderItemResponse> orderItemsResponseSet(Set<OrderItemOutput> itemOutputs) {
        return itemOutputs.stream()
                .map(OrderItemResponse::fromOutput)
                .collect(Collectors.toSet());
    }
}

record OrderItemResponse(
        String product,
        int quantity,
        BigDecimal price,
        BigDecimal total

) {
    public static OrderItemResponse fromOutput(OrderItemOutput orderItemOutput) {
        return new OrderItemResponse(
                orderItemOutput.product(),
                orderItemOutput.quantity(),
                orderItemOutput.price(),
                orderItemOutput.total()
        );
    }
}
