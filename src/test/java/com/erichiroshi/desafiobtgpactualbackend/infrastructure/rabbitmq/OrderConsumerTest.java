package com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderInput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.SaveOrderPort;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto.PedidoDTO;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @Mock
    private SaveOrderPort saveOrderPort;

    @Test
    void receber_deveConverterMensagemParaInputEChamarOUseCase() {
        PedidoDTO mensagem = new PedidoDTO(1001L, 1L, Set.of(
                new ProdutoDTO("lapis", 100, new BigDecimal("1.10"))
        ));

        OrderConsumer consumer = new OrderConsumer(saveOrderPort);
        consumer.receber(mensagem);

        ArgumentCaptor<OrderInput> captor = ArgumentCaptor.forClass(OrderInput.class);
        verify(saveOrderPort).execute(captor.capture());

        OrderInput input = captor.getValue();
        assertThat(input.orderId()).isEqualTo(1001L);
        assertThat(input.customerId()).isEqualTo(1L);
        assertThat(input.itemInputs()).hasSize(1);
    }
}
