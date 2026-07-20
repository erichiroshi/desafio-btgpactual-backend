package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres;

import com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    @EntityGraph(attributePaths = "orderItems")
    Page<OrderEntity> findAllByCustomerId(long customerId, Pageable pageable);

    long countByCustomerId(long customerId);

    @Query("""
            SELECT COALESCE(SUM(oi.price * oi.quantity), 0)
            FROM OrderEntity o
            LEFT JOIN o.orderItems oi
            WHERE o.customerId = :customerId
            """)
    BigDecimal sumTotalByCustomerId(@Param("customerId") long customerId);
}
