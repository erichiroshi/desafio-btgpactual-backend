package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.output.CustomerOrderSummaryOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindCustomerOrderSummaryPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FindCustomerOrderSummaryUseCase implements FindCustomerOrderSummaryPort {

    private static final Logger logger = LoggerFactory.getLogger(FindCustomerOrderSummaryUseCase.class);

    private final OrderRepositoryPort orderRepositoryPort;

    public FindCustomerOrderSummaryUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public CustomerOrderSummaryOutput execute(long customerId) {

        logger.info("UseCase - Buscando summary de orders | {}", customerId);

        long quantityOrders = orderRepositoryPort.countByCustomerId(customerId);
        BigDecimal total = orderRepositoryPort.sumTotalByCustomerId(customerId);

        return new CustomerOrderSummaryOutput(customerId, quantityOrders, total);
    }
}
