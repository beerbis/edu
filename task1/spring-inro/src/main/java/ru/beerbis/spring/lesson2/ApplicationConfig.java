package ru.beerbis.spring.lesson2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.math.BigDecimal;
import java.util.List;

@Configuration @ComponentScan
public class ApplicationConfig {

    @Bean
    public ProductRepository getProducts() {
        return new ProductRepository(List.of(
            new Product(1, "Носки шерстяные", BigDecimal.valueOf(200.00)),
            new Product(2, "Петушок на палочке", BigDecimal.valueOf(100.25)),
            new Product(3, "Циплёнок жареный", BigDecimal.valueOf(299.99)),
            new Product(4, "Циплёнок пареный", BigDecimal.valueOf(350.00)),
            new Product(5, "Пойти по улице гулять", BigDecimal.valueOf(0.50))
        ));
    }

    @Bean
    public Product getPresentOfTheDay() {
        return new Product(-1, "Satan's left hoof", BigDecimal.valueOf(999999999.99));
    }

    @Bean @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Cart getCart(@Qualifier("getPresentOfTheDay") Product present) {
        var cart = new Cart();
        cart.put(present);
        return cart;
    }
}
