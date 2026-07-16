package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.output.SummaryOrdersCustomerOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderClienteSummaryPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FindOrderClienteSummaryUseCase implements FindOrderClienteSummaryPort {

    private static final Logger logger = LoggerFactory.getLogger(FindOrderClienteSummaryUseCase.class);

    private final OrderRepositoryPort orderRepositoryPort;

    public FindOrderClienteSummaryUseCase(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public SummaryOrdersCustomerOutput execute(long customerId) {

        logger.info("UseCase - Buscando summary de orders | {}", customerId);

        long quantityOrders = orderRepositoryPort.countByCustomerId(customerId);
        BigDecimal total = orderRepositoryPort.sumValorTotalByCustomerId(customerId);

        return new SummaryOrdersCustomerOutput(customerId, quantityOrders, total);
    }
}
