package com.erichiroshi.desafiobtgpactualbackend.domain;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    void getTotal_deveMultiplicarPrecoPelaQuantidade() {
        OrderItem orderItem = new OrderItem("lapis", 100, new BigDecimal("1.10"));

        assertThat(orderItem.getTotal()).isEqualByComparingTo("110.00");
    }

    @Test
    void getTotal_comQuantidadeZero_deveSerZero() {
        OrderItem orderItem = new OrderItem("caderno", 0, new BigDecimal("1.00"));

        assertThat(orderItem.getTotal()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void equals_deveSerPorValorNaoPorIdentidade() {
        OrderItem a = new OrderItem("lapis", 100, new BigDecimal("1.10"));
        OrderItem b = new OrderItem("lapis", 100, new BigDecimal("1.10"));

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).hasSameHashCodeAs(b.hashCode());
    }

    @Test
    void equals_deveIgnorarDiferencaDeEscalaNoPreco() {
        // BigDecimal#equals is scale-sensitive (1.10 != 1.1 to it) - Produto#equals must not be.
        OrderItem a = new OrderItem("lapis", 100, new BigDecimal("1.10"));
        OrderItem b = new OrderItem("lapis", 100, new BigDecimal("1.1"));

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).hasSameHashCodeAs(b.hashCode());
    }

    @Test
    void equals_produtosDiferentes_naoDevemSerIguais() {
        OrderItem a = new OrderItem("lapis", 100, new BigDecimal("1.10"));
        OrderItem b = new OrderItem("caneta", 100, new BigDecimal("1.10"));

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void equals_deveRetornarFalseParaOutroTipo() {
        OrderItem a = new OrderItem("lapis", 100, new BigDecimal("1.10"));

        assertThat(a).isNotEqualTo("lapis");
    }
}
