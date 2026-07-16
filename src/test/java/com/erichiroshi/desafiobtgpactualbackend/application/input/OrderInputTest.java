package com.erichiroshi.desafiobtgpactualbackend.application.input;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderInputTest {

    @Test
    void toDomain_deveConverterInputEItensParaDominio() {
        OrderInput input = new OrderInput(1001L, 1L, Set.of(
                new OrderItemInput("lapis", 100, new BigDecimal("1.10")),
                new OrderItemInput("caderno", 10, new BigDecimal("1.00"))
        ));

        Order order = input.toDomain();

        assertThat(order.getOrderId()).isEqualTo(1001L);
        assertThat(order.getCustomerId()).isEqualTo(1L);
        assertThat(order.getItems()).hasSize(2);
        assertThat(order.getTotal()).isEqualByComparingTo("120.00");
    }
}
