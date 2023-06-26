package ru.sber.services;

import ru.sber.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация функционала - добавление продукта
 */
@Service
public class ProductService {

    private List<Product> products = new ArrayList<>(List.of(
            new Product("Со вкусом возможностей","/img/1.jpg", BigDecimal.valueOf(126)),
            new Product("Шашлык","/img/2.jpg", BigDecimal.valueOf(145)),
            new Product("Бревно","/img/3.jpg", BigDecimal.valueOf(138))

    ));

    public void addProduct(Product p) {
        products.add(p);
    }

    public List<Product> findAll() {
        return products;
    }

}