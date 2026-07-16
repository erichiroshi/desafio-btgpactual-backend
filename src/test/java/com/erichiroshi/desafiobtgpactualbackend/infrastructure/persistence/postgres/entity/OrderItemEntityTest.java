package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemEntityTest {

    @Test
    void fromDomain_eToDomain_devemFazerRoundTripSemPerdaDeDados() {
        OrderItem orderItem = new OrderItem("lapis", 100, new BigDecimal("1.10"));

        OrderItemEntity entity = OrderItemEntity.fromDomain(orderItem);
        OrderItem devolta = entity.toDomain();

        assertThat(devolta.getProduct()).isEqualTo("lapis");
        assertThat(devolta.getQuantity()).isEqualTo(100);
        assertThat(devolta.getPrice()).isEqualByComparingTo("1.10");
    }

    @Test
    void fromDomain_devePersistirTotalCalculado() {
        OrderItem orderItem = new OrderItem("lapis", 100, new BigDecimal("1.10"));

        OrderItemEntity entity = OrderItemEntity.fromDomain(orderItem);

        assertThat(entity.getTotal()).isEqualByComparingTo("110.00");
    }

    // Same reasoning as the domain Produto: this embeddable sits in an
    // @ElementCollection Set, so without value-based equals/hashCode, Hibernate
    // would compare by identity and never dedupe equal items.
    @Test
    void equals_deveSerPorValorNaoPorIdentidade() {
        OrderItemEntity a = new OrderItemEntity("lapis", 100, new BigDecimal("1.10"), new BigDecimal("110.00"));
        OrderItemEntity b = new OrderItemEntity("lapis", 100, new BigDecimal("1.10"), new BigDecimal("110.00"));

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).hasSameHashCodeAs(b.hashCode());
    }

    @Test
    void equals_deveIgnorarDiferencaDeEscalaNoPreco() {
        OrderItemEntity a = new OrderItemEntity("lapis", 100, new BigDecimal("1.10"), new BigDecimal("110.00"));
        OrderItemEntity b = new OrderItemEntity("lapis", 100, new BigDecimal("1.1"), new BigDecimal("110.0"));

        assertThat(a).isEqualTo(b);
    }

    @Test
    void equals_produtosDiferentes_naoDevemSerIguais() {
        OrderItemEntity a = new OrderItemEntity("lapis", 100, new BigDecimal("1.10"), new BigDecimal("110.00"));
        OrderItemEntity b = new OrderItemEntity("caneta", 100, new BigDecimal("1.10"), new BigDecimal("110.00"));

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void equals_deveRetornarFalseParaOutroTipo() {
        OrderItemEntity a = new OrderItemEntity("lapis", 100, new BigDecimal("1.10"), new BigDecimal("110.00"));

        assertThat(a).isNotEqualTo("lapis");
    }
}
