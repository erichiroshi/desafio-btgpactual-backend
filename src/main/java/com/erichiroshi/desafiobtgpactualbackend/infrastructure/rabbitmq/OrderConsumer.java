package com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq;

import com.erichiroshi.desafiobtgpactualbackend.application.port.in.SaveOrderPort;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.config.RabbitMqConfig;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.rabbitmq.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    private final SaveOrderPort saveOrderPort;

    public OrderConsumer(SaveOrderPort saveOrderPort) {
        this.saveOrderPort = saveOrderPort;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void receber(@Payload OrderDTO orderDTO) {

        logger.info("OrderConsumer - Receiving order: {}", orderDTO);

        saveOrderPort.execute(orderDTO.toInput());
    }

}
