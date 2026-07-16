package com.erichiroshi.desafiobtgpactualbackend.application.output;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderOutputTest {

    @Test
    void fromDomain_deveExporIdClienteProdutosEValorTotal() {
        Order order = new Order(1001L, 1L, Set.of(
                new OrderItem("lapis", 100, new BigDecimal("1.10")),
                new OrderItem("caderno", 10, new BigDecimal("1.00"))
        ));

        OrderOutput output = OrderOutput.fromDomain(order);

        assertThat(output.orderId()).isEqualTo(1001L);
        assertThat(output.customerId()).isEqualTo(1L);
        assertThat(output.itemOutputs()).hasSize(2);
        assertThat(output.total()).isEqualByComparingTo("120.00");
    }
}
