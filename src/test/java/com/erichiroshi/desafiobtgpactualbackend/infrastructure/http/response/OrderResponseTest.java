package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderResponseTest {

    @Test
    void fromOutput_deveMapearTodosOsCamposIncluindoItens() {
        Order order = new Order(1001L, 1L, Set.of(
                new OrderItem("lapis", 100, new BigDecimal("1.10"))
        ));
        OrderOutput output = OrderOutput.fromDomain(order);

        OrderResponse response = OrderResponse.fromOutput(output);

        assertThat(response.orderId()).isEqualTo(1001L);
        assertThat(response.customerId()).isEqualTo(1L);
        assertThat(response.total()).isEqualByComparingTo("110.00");
        assertThat(response.items()).hasSize(1);
    }
}
