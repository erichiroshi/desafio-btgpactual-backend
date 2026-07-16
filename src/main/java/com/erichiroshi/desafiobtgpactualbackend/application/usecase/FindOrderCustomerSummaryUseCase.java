package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.output.SummaryOrdersCustomerOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderCustomerSummaryPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FindOrderCustomerSummaryUseCase implements FindOrderCustomerSummaryPort {

    private static final Logger logger = LoggerFactory.getLogger(FindOrderCustomerSummaryUseCase.class);

    private final OrderRepositoryPort orderRepositoryPort;

    public FindOrderCustomerSummaryUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public SummaryOrdersCustomerOutput execute(long customerId) {

        logger.info("UseCase - Buscando summary de orders | {}", customerId);

        long quantityOrders = orderRepositoryPort.countByCustomerId(customerId);
        BigDecimal total = orderRepositoryPort.sumTotalByCustomerId(customerId);

        return new SummaryOrdersCustomerOutput(customerId, quantityOrders, total);
    }
}
