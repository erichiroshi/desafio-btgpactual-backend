package com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderInput;
import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderItemInput;

import java.util.Set;
import java.util.stream.Collectors;

public record PedidoDTO(
        long codigoPedido,
        long codigoCliente,
        Set<ProdutoDTO> itens) {

    public OrderInput toInput() {
        return new OrderInput(codigoPedido, codigoCliente, produtoInputSet());
    }

    private Set<OrderItemInput> produtoInputSet() {
        return itens.stream()
                .map(ProdutoDTO::toInput)
                .collect(Collectors.toSet());
    }
}
