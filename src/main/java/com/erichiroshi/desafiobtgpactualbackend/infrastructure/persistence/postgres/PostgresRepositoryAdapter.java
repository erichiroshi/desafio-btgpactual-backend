package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres;

import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PostgresRepositoryAdapter implements OrderRepositoryPort {

    private final Logger log = LoggerFactory.getLogger(PostgresRepositoryAdapter.class);

    private final OrderJpaRepository repository;

    public PostgresRepositoryAdapter(OrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {

        log.info("PostgresAdapter - Saving order: {}", order);

        OrderEntity orderEntity = OrderEntity.fromDomain(order);

        orderEntity = repository.save(orderEntity);

        return orderEntity.toDomain();
    }

    @Override
    public Page<Order> findAllByCustomerId(long customerId, Pageable pageable) {

        log.info("PostgresAdapter - List<Order> - findAllByCustomerId customerId: {}", customerId);

        Page<OrderEntity> entityPage = repository.findAllByCustomerId(customerId, pageable);

        return entityPage.map(OrderEntity::toDomain);
    }

    @Override
    public long countByCustomerId(long customerId) {

        log.info("PostgresAdapter - countByCustomerId customerId: {}", customerId);

        return repository.countByCustomerId(customerId);
    }

    @Override
    public BigDecimal sumTotalByCustomerId(long customerId) {

        log.info("PostgresAdapter - sumTotalByCustomerId customerId: {}", customerId);

        return repository.sumTotalByCustomerId(customerId);
    }
}
