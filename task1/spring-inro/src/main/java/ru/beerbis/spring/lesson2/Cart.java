package ru.beerbis.spring.lesson2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart implements Iterable<Product> {
    private List<Product> items;

    public Cart(List<Product> items) {
        this.items = new ArrayList<>(items);
    }

    public Cart() {
        items = new ArrayList<>();
    }

    @Override
    public Iterator<Product> iterator() {
        return items.iterator();
    }

    public void put(Product item) {
        items.add(item);
    }
}
