package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostgresRepositoryAdapterTest {

    @Mock
    private OrderJpaRepository repository;

    @Test
    void save_deveConverterParaEntidadeSalvarEConverterDeVoltaParaDominio() {
        Order order = new Order(1001L, 1L,
                Set.of(new OrderItem("lapis", 100, new BigDecimal("1.10"))));

        OrderEntity entityPersistido = OrderEntity.fromDomain(order);

        when(repository.save(any(OrderEntity.class))).thenReturn(entityPersistido);

        PostgresRepositoryAdapter adapter = new PostgresRepositoryAdapter(repository);
        Order resultado = adapter.save(order);

        assertThat(resultado.getOrderId()).isEqualTo(1001L);
        assertThat(resultado.getCustomerId()).isEqualTo(1L);
        assertThat(resultado.getTotal()).isEqualByComparingTo("110.00");
    }

    @Test
    void findAllByCustomerId_deveDelegarEMapearPaginaParaDominio() {
        long codigoCliente = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Order order = new Order(1001L, codigoCliente,
                Set.of(new OrderItem("lapis", 100, new BigDecimal("1.10"))));
        OrderEntity entity = OrderEntity.fromDomain(order);

        when(repository.findAllByCustomerId(codigoCliente, pageable))
                .thenReturn(new PageImpl<>(List.of(entity), pageable, 1));

        PostgresRepositoryAdapter adapter = new PostgresRepositoryAdapter(repository);
        Page<Order> resultado = adapter.findAllByCustomerId(codigoCliente, pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().getFirst().getOrderId()).isEqualTo(1001L);

        verify(repository).findAllByCustomerId(codigoCliente, pageable);
    }

    @Test
    void countByCustomerId_deveDelegarParaORepositorio() {
        when(repository.countByCustomerId(1L)).thenReturn(3L);

        PostgresRepositoryAdapter adapter = new PostgresRepositoryAdapter(repository);

        assertThat(adapter.countByCustomerId(1L)).isEqualTo(3L);
        verify(repository).countByCustomerId(1L);
    }

    @Test
    void sumTotalByCustomerId_deveDelegarParaORepositorio() {
        when(repository.sumTotalByCustomerId(1L)).thenReturn(new BigDecimal("450.00"));

        PostgresRepositoryAdapter adapter = new PostgresRepositoryAdapter(repository);

        assertThat(adapter.sumTotalByCustomerId(1L)).isEqualByComparingTo("450.00");
        verify(repository).sumTotalByCustomerId(1L);
    }
}
