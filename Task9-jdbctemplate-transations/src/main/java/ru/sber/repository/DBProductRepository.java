package ru.sber.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.sber.entity.Product;
@Repository
public class DBProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Product product) {
        var insertSql = "INSERT INTO products_vanchugova_da.PRODUCT (name, price,count) VALUES (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice().doubleValue());
            preparedStatement.setInt(3,product.getQuantity());
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (long) (int) keyHolder.getKeys().get("id");
    }

    @Override
    public Optional<Product> findById(long productId) {
        var selectSql = "SELECT * FROM products_vanchugova_da.PRODUCT where id = ?";
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setLong(1, productId);

            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        List<Product> products = jdbcTemplate.query(preparedStatementCreator, productRowMapper);

        return products.stream().findFirst();
    }

    @Override
    public List<Product> findAll(String productName) {
        var selectSql = "SELECT * FROM products_vanchugova_da.PRODUCT where name like ?";
        List<Product> products = new ArrayList<>();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        return jdbcTemplate.query(preparedStatementCreator, productRowMapper);
    }


    @Override
    public boolean deleteById(long id) {
        var deleteSql = "DELETE FROM products_vanchugova_da.PRODUCT where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, id);

            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

    @Override
    public boolean update(Product product) {
        var updateSql = """
                UPDATE products_vanchugova_da.PRODUCT
                SET 
                name = ?,
                price = ?,
                count = ?
                where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setInt(3,product.getQuantity());
            prepareStatement.setLong(4, product.getId());

            return prepareStatement;
        };

        int  rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;


    }

    private static RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            Integer quantity = resultSet.getInt("count");
            return new Product(id, name, BigDecimal.valueOf(price),quantity);
        };
    }
}
