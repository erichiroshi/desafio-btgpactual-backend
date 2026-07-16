package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderInput;
import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderItemInput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalvaOrderUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Test
    void execute_deveConverterInputParaDominioEChamarRepositorio() {
        OrderInput input = new OrderInput(1001L, 1L,
                Set.of(new OrderItemInput("lapis", 100, new BigDecimal("1.10"))));

        when(orderRepositoryPort.save(org.mockito.ArgumentMatchers.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SaveOrderUseCase useCase = new SaveOrderUseCase(orderRepositoryPort);
        useCase.execute(input);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepositoryPort).save(captor.capture());

        Order salvo = captor.getValue();
        assertThat(salvo.getOrderId()).isEqualTo(1001L);
        assertThat(salvo.getCustomerId()).isEqualTo(1L);
        assertThat(salvo.valorTotal()).isEqualByComparingTo("110.00");
    }
}
