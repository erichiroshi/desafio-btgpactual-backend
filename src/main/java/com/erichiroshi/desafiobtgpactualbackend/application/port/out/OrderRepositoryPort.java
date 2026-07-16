package com.erichiroshi.desafiobtgpactualbackend.application.port.out;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface OrderRepositoryPort {

    Order save(Order order);

    Page<Order> findAllByCustomerId(long customerId, Pageable pageable);

    long countByCustomerId(long customerId);

    BigDecimal sumTotalByCustomerId(long customerId);
}
