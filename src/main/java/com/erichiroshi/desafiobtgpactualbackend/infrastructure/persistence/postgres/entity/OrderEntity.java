package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import com.erichiroshi.desafiobtgpactualbackend.domain.model.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_order")
public class OrderEntity {

    @Id
    private long orderId;

    private long customerId;

    private BigDecimal total;

    @ElementCollection
    @CollectionTable(
            name = "tb_order_order_item",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private Set<OrderItemEntity> orderItems = new HashSet<>();

    public OrderEntity() {
    }

    public OrderEntity(long orderId, long customerId, Set<OrderItemEntity> orderItems, BigDecimal total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.total = total;
    }

    public Order toDomain() {
        return new Order(orderId, customerId, orderItemsSet());
    }

    private Set<OrderItem> orderItemsSet() {
        return orderItems.stream()
                .map(OrderItemEntity::toDomain)
                .collect(Collectors.toSet());
    }

    public static OrderEntity fromDomain(Order order) {
        return new OrderEntity(
                order.getOrderId(),
                order.getCustomerId(),
                orderItemEntitiesSet(order),
                order.getTotal());
    }

    private static Set<OrderItemEntity> orderItemEntitiesSet(Order order) {
        return order.getItems().stream()
                .map(OrderItemEntity::fromDomain)
                .collect(Collectors.toSet());
    }

    public BigDecimal getTotal() {
        return total;
    }
}