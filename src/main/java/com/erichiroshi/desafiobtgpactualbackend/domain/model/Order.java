package com.erichiroshi.desafiobtgpactualbackend.domain.model;

import java.math.BigDecimal;
import java.util.Set;

public class Order {

    private final long orderId;
    private final long customerId;
    private final Set<OrderItem> items;

    public Order(long orderId, long customerId, Set<OrderItem> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public BigDecimal valorTotal() {
        return items.stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
