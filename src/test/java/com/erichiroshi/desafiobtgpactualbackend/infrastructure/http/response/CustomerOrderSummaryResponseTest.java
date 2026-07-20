package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response;

import com.erichiroshi.desafiobtgpactualbackend.application.output.CustomerOrderSummaryOutput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerOrderSummaryResponseTest {

    @Test
    void fromOutput_deveMapearTodosOsCampos() {
        CustomerOrderSummaryOutput output = new CustomerOrderSummaryOutput(1L, 3L, new BigDecimal("450.00"));

        CustomerOrderSummaryResponse response = CustomerOrderSummaryResponse.fromOutput(output);

        assertThat(response.customerId()).isEqualTo(1L);
        assertThat(response.quantityOrders()).isEqualTo(3L);
        assertThat(response.total()).isEqualByComparingTo("450.00");
    }
}
