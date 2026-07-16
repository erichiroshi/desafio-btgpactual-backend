package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response;

import com.erichiroshi.desafiobtgpactualbackend.application.output.SummaryOrdersCustomerOutput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SummaryCustomerOrderResponseTest {

    @Test
    void fromOutput_deveMapearTodosOsCampos() {
        SummaryOrdersCustomerOutput output = new SummaryOrdersCustomerOutput(1L, 3L, new BigDecimal("450.00"));

        SummaryCustomerOrderResponse response = SummaryCustomerOrderResponse.fromOutput(output);

        assertThat(response.customerId()).isEqualTo(1L);
        assertThat(response.quantityOrders()).isEqualTo(3L);
        assertThat(response.total()).isEqualByComparingTo("450.00");
    }
}
