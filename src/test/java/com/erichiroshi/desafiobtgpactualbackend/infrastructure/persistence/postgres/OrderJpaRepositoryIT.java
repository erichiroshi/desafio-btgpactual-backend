package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderJpaRepositoryIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"));

    @Autowired
    private OrderJpaRepository repository;

    @Test
    void sumValorTotalByCustomerId() {
        long codigoCliente = 1L;

        // Pedido 1: 100 * 1.10 + 10 * 1.00 = 120.00
        salvarPedido(1001L, codigoCliente,
                new OrderItem("lapis", 100, new BigDecimal("1.10")),
                new OrderItem("caderno", 10, new BigDecimal("1.00")));
        // Pedido 2: 5 * 2.00 = 10.00
        salvarPedido(1002L, codigoCliente,
                new OrderItem("borracha", 5, new BigDecimal("2.00")));
        // Outro cliente - não deve entrar na soma nem na contagem
        salvarPedido(1003L, 2L,
                new OrderItem("caneta", 1, new BigDecimal("5.00")));

        BigDecimal total = repository.sumValorTotalByCustomerId(codigoCliente);
        long quantidade = repository.countByCustomerId(codigoCliente);

        assertThat(total).isEqualByComparingTo("130.00");
        assertThat(quantidade).isEqualTo(2L);
    }

    @Test
    void sumValorTotalByCustomerIdSemPedidos_deveRetornarZero() {
        BigDecimal total = repository.sumValorTotalByCustomerId(999L);
        long quantidade = repository.countByCustomerId(999L);

        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(quantidade).isZero();
    }

    private void salvarPedido(long id, long codigoCliente, OrderItem... orderItems) {
        Order order = new Order(id, codigoCliente, Set.of(orderItems));
        OrderEntity entity = OrderEntity.fromDomain(order);
        repository.save(entity);
    }
}
