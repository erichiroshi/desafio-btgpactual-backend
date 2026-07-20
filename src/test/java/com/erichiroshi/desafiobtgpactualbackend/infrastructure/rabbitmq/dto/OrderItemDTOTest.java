package com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderItemInput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemDTOTest {

    @Test
    void toInput_deveConverterItemDaMensagemParaProdutoInput() {
        OrderItemDTO dto = new OrderItemDTO("lapis", 100, new BigDecimal("1.10"));

        OrderItemInput input = dto.toInput();

        assertThat(input.product()).isEqualTo("lapis");
        assertThat(input.quantity()).isEqualTo(100);
        assertThat(input.price()).isEqualByComparingTo("1.10");
    }
}
