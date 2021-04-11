package ru.beerbis.spring.lesson2;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductRepository {
    private final List<Product> productList;
    private final Map<Integer, Product> productMap;

    public ProductRepository(List<Product> productList) {
        this.productList = Collections.unmodifiableList(productList);
        productMap = productList.stream().collect(Collectors.toUnmodifiableMap(Product::getId, it -> it));
    }

    public List<Product> getList() {
        return productList;
    }

    public Optional<Product> getProduct(Integer id) {
        return Optional.ofNullable(productMap.get(id));
    }
}
