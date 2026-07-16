package com.erichiroshi.desafiobtgpactualbackend.application.output;

import java.math.BigDecimal;

public record SummaryOrdersCustomerOutput(
        long CustomerId,
        long quantityOrder,
        BigDecimal total
) {
}
