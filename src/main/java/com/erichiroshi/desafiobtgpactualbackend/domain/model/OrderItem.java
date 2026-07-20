package com.erichiroshi.desafiobtgpactualbackend.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record OrderItem(String product, int quantity, BigDecimal price) {

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem(String product1, int quantity1, BigDecimal price1))) return false;
        return Objects.equals(product, product1) &&
                Objects.equals(quantity, quantity1) &&
                (price == null ? price1 == null : price.compareTo(price1) == 0);
    }

    @Override
    public int hashCode() {
        Object priceHash = (price == null) ? null : price.stripTrailingZeros();
        return Objects.hash(product, quantity, priceHash);
    }
}
