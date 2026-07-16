package com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderInput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDTOTest {

    @Test
    void toInput_deveConverterMensagemParaPedidoInput() {
        PedidoDTO dto = new PedidoDTO(1001L, 1L, Set.of(
                new ProdutoDTO("lapis", 100, new BigDecimal("1.10")),
                new ProdutoDTO("caderno", 10, new BigDecimal("1.00"))
        ));

        OrderInput input = dto.toInput();

        assertThat(input.orderId()).isEqualTo(1001L);
        assertThat(input.customerId()).isEqualTo(1L);
        assertThat(input.itemInputs()).hasSize(2);
    }
}
