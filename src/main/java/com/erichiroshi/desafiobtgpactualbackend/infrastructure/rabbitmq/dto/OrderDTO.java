package com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderInput;
import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderItemInput;

import java.util.Set;
import java.util.stream.Collectors;

public record OrderDTO(
        long codigoPedido,
        long codigoCliente,
        Set<OrderItemDTO> itens) {

    public OrderInput toInput() {
        return new OrderInput(codigoPedido, codigoCliente, orderItemInputSet());
    }

    private Set<OrderItemInput> orderItemInputSet() {
        return itens.stream()
                .map(OrderItemDTO::toInput)
                .collect(Collectors.toSet());
    }
}
