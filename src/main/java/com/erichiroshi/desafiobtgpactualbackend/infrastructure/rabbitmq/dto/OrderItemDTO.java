package com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderItemInput;

import java.math.BigDecimal;

public record OrderItemDTO(
        String produto,
        int quantidade,
        BigDecimal preco
) {

    public OrderItemInput toInput() {
        return new OrderItemInput(produto, quantidade, preco);
    }
}
