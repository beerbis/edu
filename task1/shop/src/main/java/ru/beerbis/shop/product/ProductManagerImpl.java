package ru.beerbis.shop.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ProductManagerImpl implements ProductManager {
    private static Random rnd = new Random();
    private static String[] names = new String[]{
        "Фуфломицин", "Репа", "Селёдка", "Крабулятор", "Кран", "Сахар", "Масло", "А-Тата-сол", "Куркума", "Копмот",
            "Колумбарий", "Рататуй", "Табурет", "Кило опилок", "Бурато", "Борат", "Бармалей", "Бирабиджан"
    };

    @Override
    public List<Product> get() {
        List<Product> products = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            products.add(randomOne());
        }
        return products;
    }

    private static Product randomOne(){
        return new Product(rnd.nextInt(99999), names[rnd.nextInt(names.length)], BigDecimal.valueOf(rnd.nextInt(999999999), 2));
    }
}
