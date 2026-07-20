package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response;

import com.erichiroshi.desafiobtgpactualbackend.application.output.CustomerOrderSummaryOutput;

import java.math.BigDecimal;

public record CustomerOrderSummaryResponse(
        long customerId,
        long quantityOrders,
        BigDecimal total) {

    public static CustomerOrderSummaryResponse fromOutput(CustomerOrderSummaryOutput output) {
        return new CustomerOrderSummaryResponse(
                output.customerId(),
                output.quantityOrder(),
                output.total());
    }
}
