package ru.sber.repository;

import java.util.List;
import java.util.Optional;
import ru.sber.entity.Product;

/**
 *
 */
public interface ProductRepository {


    long saveProduct(Product product);

    Optional<Product> findProductById(long id);

    List<Product> findAllProduct(String name);

    boolean updateProduct(Product product);

    boolean deleteProductById(long id);
}
