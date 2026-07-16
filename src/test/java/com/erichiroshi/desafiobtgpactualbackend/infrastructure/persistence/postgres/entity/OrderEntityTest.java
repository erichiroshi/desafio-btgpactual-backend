package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderEntityTest {

    @Test
    void fromDomain_eToDomain_devemFazerRoundTripSemPerdaDeDados() {
        Order order = new Order(1001L, 1L, Set.of(
                new OrderItem("lapis", 100, new BigDecimal("1.10")),
                new OrderItem("caderno", 10, new BigDecimal("1.00"))
        ));

        OrderEntity entity = OrderEntity.fromDomain(order);
        Order devolta = entity.toDomain();

        assertThat(devolta.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(devolta.getCustomerId()).isEqualTo(order.getCustomerId());
        assertThat(devolta.getItems()).hasSize(2);
        assertThat(devolta.getTotal()).isEqualByComparingTo(order.getTotal());
    }

    @Test
    void fromDomain_devePersistirTotalCalculadoDoDominio() {
        Order order = new Order(1001L, 1L, Set.of(
                new OrderItem("lapis", 100, new BigDecimal("1.10"))
        ));

        OrderEntity entity = OrderEntity.fromDomain(order);

        assertThat(entity.getTotal()).isEqualByComparingTo("110.00");
    }
}
