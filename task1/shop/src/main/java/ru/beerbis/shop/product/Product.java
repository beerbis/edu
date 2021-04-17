package ru.beerbis.shop.product;

import java.math.BigDecimal;

public class Product {
    private final Integer id;
    private final String title;
    private final BigDecimal cost;

    public Product(Integer id, String title, BigDecimal cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }
}
