package com.erichiroshi.desafiobtgpactualbackend.domain;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void valorTotal_deveSomarValorTotalDeCadaProduto() {
        OrderItem lapis = new OrderItem("lapis", 100, new BigDecimal("1.10"));
        OrderItem caderno = new OrderItem("caderno", 10, new BigDecimal("1.00"));

        Order order = new Order(1001L, 1L, Set.of(lapis, caderno));

        // 100 * 1.10 + 10 * 1.00 = 110.00 + 10.00 = 120.00
        assertThat(order.valorTotal()).isEqualByComparingTo("120.00");
    }

    @Test
    void valorTotal_semProdutos_deveSerZero() {
        Order order = new Order(1002L, 1L, Set.of());

        assertThat(order.valorTotal()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void produtosIguais_devemSerDeduplicadosNoSet() {
        OrderItem a = new OrderItem("lapis", 100, new BigDecimal("1.10"));
        OrderItem b = new OrderItem("lapis", 100, new BigDecimal("1.10"));

        Set<OrderItem> orderItems = new java.util.HashSet<>();
        orderItems.add(a);
        orderItems.add(b);

        assertThat(orderItems).hasSize(1);
    }
}
