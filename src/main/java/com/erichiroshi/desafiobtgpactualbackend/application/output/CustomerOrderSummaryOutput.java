package com.erichiroshi.desafiobtgpactualbackend.application.output;

import java.math.BigDecimal;

public record CustomerOrderSummaryOutput(
        long customerId,
        long quantityOrder,
        BigDecimal total
) {
}
