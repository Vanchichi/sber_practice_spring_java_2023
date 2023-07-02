package ru.sber.service;

import java.util.List;
import java.util.Optional;
import ru.sber.entity.Product;


public interface ProductService {
    long save(Product product);

    Optional<Product> findById(long id);

    List<Product> findAll(String name);

    boolean update(Product product);

    boolean deleteById(long id);
}
