package com.erichiroshi.desafiobtgpactualbackend.application.output;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemOutputTest {

    @Test
    void fromDomain_deveExporCamposEGetTotal() {
        OrderItem orderItem = new OrderItem("lapis", 100, new BigDecimal("1.10"));

        OrderItemOutput output = OrderItemOutput.fromDomain(orderItem);

        assertThat(output.product()).isEqualTo("lapis");
        assertThat(output.quantity()).isEqualTo(100);
        assertThat(output.price()).isEqualByComparingTo("1.10");
        assertThat(output.total()).isEqualByComparingTo("110.00");
    }
}
