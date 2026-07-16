package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderInput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.SaveOrderPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SaveOrderUseCase implements SaveOrderPort {

    private static final Logger logger = LoggerFactory.getLogger(SaveOrderUseCase.class);

    private final OrderRepositoryPort orderRepositoryPort;

    public SaveOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public void execute(OrderInput input) {

        logger.info("UseCase - Salvando Pedido | {}", input);

        Order order = orderRepositoryPort.save(input.toDomain());

        logger.info("UseCase - Pedido salvo | {}", order);

    }
}
