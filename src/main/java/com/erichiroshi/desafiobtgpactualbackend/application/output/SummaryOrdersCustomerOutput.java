package com.erichiroshi.desafiobtgpactualbackend.application.output;

import java.math.BigDecimal;

public record SummaryOrdersCustomerOutput(
        long customerId,
        long quantityOrder,
        BigDecimal total
) {
}
