package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response;

import com.erichiroshi.desafiobtgpactualbackend.application.output.SummaryOrdersCustomerOutput;

import java.math.BigDecimal;

public record SummaryCustomerOrderResponse(
        long customerId,
        long quantityOrders,
        BigDecimal total) {

    public static SummaryCustomerOrderResponse fromOutput(SummaryOrdersCustomerOutput output) {
        return new SummaryCustomerOrderResponse(
                output.CustomerId(),
                output.quantityOrder(),
                output.total());
    }
}
