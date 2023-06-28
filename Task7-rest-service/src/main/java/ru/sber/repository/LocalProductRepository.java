package ru.sber.repository;
import org.springframework.stereotype.Repository;
import ru.sber.entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class LocalProductRepository implements ProductRepository {
    private List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Яблоко", 50,23),
            new Product(2, "Арбуз", 150,16),
            new Product(3, "Персик", 30,32)
    ));

    @Override
    public long saveProduct(Product product) {
        long id = generateIdProduct();
        product.setId(id);

        products.add(product);
        return id;
    }

    @Override
    public Product getProductById(long id) {

        return products.get((int)id);
    }
    @Override
    public Optional<Product> findProductById(long id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findAny();
    }

    @Override
    public List<Product> findAllProduct(String name) {
        if (name == null) {
            return products;
        }

        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    @Override
    public boolean updateProduct(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteProductById(long id) {
        return products.removeIf(product -> product.getId() == id);
    }

    private long generateIdProduct() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}
