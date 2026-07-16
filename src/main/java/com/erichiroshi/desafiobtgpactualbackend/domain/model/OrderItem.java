package com.erichiroshi.desafiobtgpactualbackend.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private final String product;
    private final int quantity;
    private final BigDecimal price;

    public OrderItem(String product, int quantity, BigDecimal price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem other)) return false;
        return quantity == other.quantity
                && Objects.equals(product, other.product)
                && price.doubleValue() == other.price.doubleValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, price.doubleValue());
    }
}
