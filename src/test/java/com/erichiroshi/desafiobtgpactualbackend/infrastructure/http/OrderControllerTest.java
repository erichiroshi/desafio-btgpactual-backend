package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.output.SummaryOrdersCustomerOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderClienteSummaryPort;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FindOrderPort findOrderPort;

    @MockitoBean
    private FindOrderClienteSummaryPort findOrderClienteSummaryPort;

    @Test
    void getOrdersSummary_deveRetornarQuantidadeEValorTotalDoCliente() throws Exception {
        long codigoCliente = 1L;
        SummaryOrdersCustomerOutput output = new SummaryOrdersCustomerOutput(
                codigoCliente, 3L, new BigDecimal("450.00"));

        when(findOrderClienteSummaryPort.execute(codigoCliente)).thenReturn(output);

        mockMvc.perform(get("/customers/{id}/orders/summary", codigoCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(codigoCliente))
                .andExpect(jsonPath("$.quantityOrders").value(3))
                .andExpect(jsonPath("$.total").value(450.00));
    }

    @Test
    void getOrders_deveExporValorTotalExplicitoPorPedidoEResumoDoCliente() throws Exception {
        long codigoCliente = 1L;

        OrderOutput orderOutput = OrderOutput.fromDomain(
                new Order(
                        1001L,
                        codigoCliente,
                        Set.of(new OrderItem("lapis", 100, new BigDecimal("1.10")))
                )
        );

        Page<OrderOutput> page = new PageImpl<>(List.of(orderOutput));
        SummaryOrdersCustomerOutput resumo = new SummaryOrdersCustomerOutput(
                codigoCliente, 1L, new BigDecimal("110.00"));

        when(findOrderPort.execute(anyLong(), any(Pageable.class))).thenReturn(page);
        when(findOrderClienteSummaryPort.execute(codigoCliente)).thenReturn(resumo);

        mockMvc.perform(get("/customers/{id}/orders", codigoCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders.content[0].total").value(110.00))
                .andExpect(jsonPath("$.summary.quantityOrders").value(1))
                .andExpect(jsonPath("$.summary.totalAmountOrders").value(110.00));
    }
}
