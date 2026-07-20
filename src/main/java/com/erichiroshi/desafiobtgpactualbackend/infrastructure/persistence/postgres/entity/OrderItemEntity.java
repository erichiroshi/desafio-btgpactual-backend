package com.erichiroshi.desafiobtgpactualbackend.infrastructure.persistence.postgres.entity;

import com.erichiroshi.desafiobtgpactualbackend.domain.model.OrderItem;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class OrderItemEntity {

    private String product;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    public OrderItemEntity() {
    }

    public OrderItemEntity(String product, int quantity, BigDecimal price, BigDecimal total) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public OrderItem toDomain() {
        return new OrderItem(product, quantity, price);
    }

    public static OrderItemEntity fromDomain(OrderItem orderItem) {
        return new OrderItemEntity(
                orderItem.product(),
                orderItem.quantity(),
                orderItem.price(),
                orderItem.getTotal());
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemEntity other)) return false;
        return quantity == other.quantity
                && java.util.Objects.equals(product, other.product)
                && price.doubleValue() == other.price.doubleValue();
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(product, quantity, price.doubleValue());
    }
}