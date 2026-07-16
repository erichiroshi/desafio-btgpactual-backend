package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindOrderUseCase implements FindOrderPort {

    private static final Logger logger = LoggerFactory.getLogger(FindOrderUseCase.class);

    private final OrderRepositoryPort orderRepositoryPort;

    public FindOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public Page<OrderOutput> execute(long customerId, Pageable pageable) {

        logger.info("UseCase - Buscando Pedido | {}", customerId);

        Page<Order> pageOrder = orderRepositoryPort.findAllByCustomerId(customerId, pageable);

        logger.info("UseCase - Pedido list | {}", pageOrder);

        return pageOrder.map(OrderOutput::fromDomain);
    }
}
