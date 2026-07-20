package com.erichiroshi.desafiobtgpactualbackend.application.input;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemInputTest {

    @Test
    void toDomain_deveConverterParaProdutoDeDominio() {
        OrderItemInput input = new OrderItemInput("lapis", 100, new BigDecimal("1.10"));

        OrderItem orderItem = input.toDomain();

        assertThat(orderItem.product()).isEqualTo("lapis");
        assertThat(orderItem.quantity()).isEqualTo(100);
        assertThat(orderItem.price()).isEqualByComparingTo("1.10");
    }
}
