package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.output.CustomerOrderSummaryOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscaResumoPedidosClienteUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Test
    void execute_deveCombinarContagemESomaDoRepositorio() {
        long codigoCliente = 1L;

        when(orderRepositoryPort.countByCustomerId(codigoCliente)).thenReturn(3L);
        when(orderRepositoryPort.sumTotalByCustomerId(codigoCliente)).thenReturn(new BigDecimal("450.00"));

        FindCustomerOrderSummaryUseCase useCase = new FindCustomerOrderSummaryUseCase(orderRepositoryPort);

        CustomerOrderSummaryOutput output = useCase.execute(codigoCliente);

        assertThat(output.customerId()).isEqualTo(codigoCliente);
        assertThat(output.quantityOrder()).isEqualTo(3L);
        assertThat(output.total()).isEqualByComparingTo("450.00");

        verify(orderRepositoryPort).countByCustomerId(codigoCliente);
        verify(orderRepositoryPort).sumTotalByCustomerId(codigoCliente);
    }

    @Test
    void execute_clienteSemPedidos_deveRetornarZerado() {
        long codigoCliente = 2L;

        when(orderRepositoryPort.countByCustomerId(codigoCliente)).thenReturn(0L);
        when(orderRepositoryPort.sumTotalByCustomerId(codigoCliente)).thenReturn(BigDecimal.ZERO);

        FindCustomerOrderSummaryUseCase useCase = new FindCustomerOrderSummaryUseCase(orderRepositoryPort);

        CustomerOrderSummaryOutput output = useCase.execute(codigoCliente);

        assertThat(output.quantityOrder()).isZero();
        assertThat(output.total()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
