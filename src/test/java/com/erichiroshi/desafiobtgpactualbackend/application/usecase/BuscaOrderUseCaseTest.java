package com.erichiroshi.desafiobtgpactualbackend.application.usecase;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.out.OrderRepositoryPort;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscaOrderUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Test
    void execute_deveMapearPaginaDeDominioParaPaginaDeOutput() {
        long codigoCliente = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Order order = new Order(1001L, codigoCliente,
                Set.of(new OrderItem("lapis", 100, new BigDecimal("1.10"))));

        Page<Order> pageDominio = new PageImpl<>(List.of(order), pageable, 1);

        when(orderRepositoryPort.findAllByCustomerId(codigoCliente, pageable)).thenReturn(pageDominio);

        FindOrderUseCase useCase = new FindOrderUseCase(orderRepositoryPort);

        Page<OrderOutput> resultado = useCase.execute(codigoCliente, pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().getFirst().orderId()).isEqualTo(1001L);
        assertThat(resultado.getContent().getFirst().total()).isEqualByComparingTo("110.00");

        verify(orderRepositoryPort).findAllByCustomerId(codigoCliente, pageable);
    }

    @Test
    void execute_clienteSemPedidos_deveRetornarPaginaVazia() {
        long codigoCliente = 2L;
        Pageable pageable = PageRequest.of(0, 10);

        when(orderRepositoryPort.findAllByCustomerId(codigoCliente, pageable))
                .thenReturn(Page.empty(pageable));

        FindOrderUseCase useCase = new FindOrderUseCase(orderRepositoryPort);

        Page<OrderOutput> resultado = useCase.execute(codigoCliente, pageable);

        assertThat(resultado.getContent()).isEmpty();
    }
}
