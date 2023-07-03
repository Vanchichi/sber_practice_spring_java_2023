package ru.sber.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import ru.sber.entity.Cart;
import ru.sber.entity.Client;
import ru.sber.entity.Product;
import ru.sber.proxy.ClientBankProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class  DBCartRepository implements CartRepository {

    private final ClientBankProxy bankProxy;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBCartRepository(JdbcTemplate jdbcTemplate, ClientBankProxy bankProxy) {
        this.jdbcTemplate = jdbcTemplate;
        this.bankProxy = bankProxy;
    }


    @Override
    public boolean addProductFromCartById(long idCart, long idProduct, Integer quantity) {
        var insertSql = """
                INSERT INTO products_vanchugova_da.PRODUCT_CLIENT
                (ID_CART, ID_PRODUCT, COUNT)
                VALUES 
                (?,?,?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, idCart);
            preparedStatement.setLong(2, idProduct);
            preparedStatement.setInt(3, quantity);
            return preparedStatement;
        };

        var rows = jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return rows > 0;
    }


    @Override
    public boolean changeQuantity(long idCart, long idProduct, Integer quantity) {
        var updateSql = """
                UPDATE  products_vanchugova_da.PRODUCT_CLIENT
                SET COUNT=?
                WHERE ID_CART=? AND ID_PRODUCT=?
                """;
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setInt(1, quantity);
            prepareStatement.setLong(2, idCart);
            prepareStatement.setLong(3, idProduct);
            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }


    @Override
    public boolean deleteProductFromCartById(long idCart, long idProduct) {
        var deleteSql = """
                DELETE FROM products_vanchugova_da.PRODUCT_CLIENT
                WHERE ((ID_CART = ?) AND (ID_PRODUCT = ?))
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, idCart);
            prepareStatement.setLong(2, idProduct);
            return prepareStatement;
        };

        var rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }
    @Transactional
    @Override
    public BigDecimal payment(long idCart, long idCard) {
        BigDecimal cartTotal = calculateCartTotal(idCart);
        BigDecimal accountBalance = bankProxy.getAccountByIdCard(idCard);

        if (cartTotal.compareTo(accountBalance) > 0) {
            throw new RuntimeException("Недостаточно средств на карте");
        }

        if (!validateProductQuantities(idCart)) {
            throw new RuntimeException("Количество товара в корзине превышает количество товара на складе");
        }

        updateProductQuantities(idCart);
        changeTotalFromAccountBalance(idCard, cartTotal);
        deleteCartProducts(idCart);

        return cartTotal;
    }

    private BigDecimal calculateCartTotal(long idCart) {
        String sql = "SELECT SUM(P.PRICE * PC.COUNT) FROM PRODUCT P JOIN PRODUCT_CLIENT PC ON PC.ID_PRODUCT = P.ID WHERE PC.ID_CART = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, idCart);
    }

    private boolean validateProductQuantities(long idCart) {
        String sql = """
                SELECT PC.COUNT <= P.COUNT 
                FROM PRODUCT_CLIENT PC 
                JOIN PRODUCT P ON PC.ID_PRODUCT = P.ID 
                WHERE PC.ID_CART = ?
                """;
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, idCart);
        return result;
    }

    private void updateProductQuantities(long idCart) {
        String sql = """
                UPDATE PRODUCT
                                          SET COUNT =  (
                                          SELECT P.COUNT- PC.COUNT MINUS
                                          FROM PRODUCT P
                                          JOIN PRODUCT_CLIENT PC
                                          ON P.ID=PC.ID_PRODUCT
                                          )
                                          WHERE  ID=
                                          (SELECT ID_PRODUCT
                                          FROM PRODUCT_CLIENT
                                          WHERE ID_CART=?
                                          )
                """;
        jdbcTemplate.update(sql, idCart);
    }

    private void changeTotalFromAccountBalance(long idCard, BigDecimal sum) {
        bankProxy.changeAccount(idCard, sum);
    }

    private void deleteCartProducts(long idCart) {
        String sql = "DELETE FROM PRODUCT_CLIENT WHERE ID_CART = ?";
        jdbcTemplate.update(sql, idCart);
    }
}
