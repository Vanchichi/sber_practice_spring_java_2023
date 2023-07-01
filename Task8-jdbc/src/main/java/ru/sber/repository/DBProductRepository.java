package ru.sber.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.sber.entity.Product;
@Repository
public class DBProductRepository implements ProductRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    @Override
    public long saveProduct(Product product) {
        var insertSql = "INSERT INTO products_vanchugova_da.PRODUCT (name, price,count) VALUES (?,?,?);";
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setBigDecimal(2, product.getPrice());
            prepareStatement.setInt(3,product.getQuantity());
            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findProductById(long productId) {
        var selectSql = "SELECT * FROM products_vanchugova_da.PRODUCT where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, productId);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                Integer quantity =resultSet.getInt("count");
                Product product = new Product(id, name, price,quantity);

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Product> findAllProduct(String productName) {
        var selectSql = "SELECT * FROM products_vanchugova_da.PRODUCT where name like ?";
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                Integer quantity =resultSet.getInt("count");
                Product product = new Product(id, name, price,quantity);

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProductById(long id) {
        var selectSql = "DELETE FROM products_vanchugova_da.PRODUCT where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, id);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        var selectSql = """
                UPDATE products_vanchugova_da.PRODUCT
                SET 
                name = ?,
                price = ?,
                count = ?
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setBigDecimal(2, product.getPrice());
            prepareStatement.setInt(3, product.getQuantity());
            prepareStatement.setLong(4, product.getId());

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
