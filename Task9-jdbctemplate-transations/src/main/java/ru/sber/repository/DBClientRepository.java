package ru.sber.repository;

import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.sber.entity.Cart;
import ru.sber.entity.Client;
import ru.sber.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class DBClientRepository implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public long save(Client client) {
        var insertSql1 = "INSERT INTO products_vanchugova_da.CLIENT (name,username,password,cart_id) VALUES (?,?,?,?);";
        var insertSql2 = "INSERT INTO products_vanchugova_da.CART (promocode) VALUES (?)";
        KeyHolder clientKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator2 = connection -> {
            PreparedStatement preparedStatement2 = connection.prepareStatement(insertSql2, Statement.RETURN_GENERATED_KEYS);
            preparedStatement2.setString(1, "NEW50");
            return preparedStatement2;
        };

        jdbcTemplate.update(preparedStatementCreator2, clientKeyHolder);

        long cartId = (long) (int) clientKeyHolder.getKeys().get("id");

        PreparedStatementCreator preparedStatementCreator1 = connection -> {
            PreparedStatement preparedStatement1 = connection.prepareStatement(insertSql1, Statement.RETURN_GENERATED_KEYS);
            preparedStatement1.setString(1, client.getName());
            preparedStatement1.setString(2, client.getLogin());
            preparedStatement1.setString(3, client.getPassword());
            preparedStatement1.setLong(4, cartId);
            return preparedStatement1;
        };

        jdbcTemplate.update(preparedStatementCreator1, clientKeyHolder);

        return cartId;
    }

    @Override
    public Optional<Client> findById(long idClient) {
        String selectSql = """
            SELECT CL.NAME, CL.USERNAME, CL.PASSWORD, CL.CART_ID, CA.PROMOCODE
            FROM products_vanchugova_da.CLIENT CL
            JOIN products_vanchugova_da.CART CA ON CL.CART_ID = CA.ID
            WHERE CL.ID = ?
        """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setInt(1, (int) idClient);
            return prepareStatement;
        };

        RowMapper<Client> clientRowMapper = (resultSet, rowNum) -> {
            String name = resultSet.getString("name");
            String login = resultSet.getString("username");
            String password = resultSet.getString("password");
            int idCart = resultSet.getInt("cart_id");
            String promoCode = "NEW50";
            List<Product> productList=productList(idClient);
            Cart cart = new Cart( idCart, productList, promoCode);
            Client client = new Client(idClient, name, login, password, cart);
            return  client;
        };
         return jdbcTemplate.query(preparedStatementCreator, clientRowMapper).stream().findAny();
        }

    private List<Product> productList (long idClient){
        String selectSql = """
            SELECT P.ID, P.NAME, P.PRICE, PC.COUNT
            FROM products_vanchugova_da.CLIENT CL
            JOIN products_vanchugova_da.CART CA ON CL.CART_ID = CA.ID
            JOIN products_vanchugova_da.PRODUCT_CLIENT PC ON CA.ID = PC.ID_CART
            JOIN products_vanchugova_da.PRODUCT P ON PC.ID_PRODUCT = P.ID
            WHERE CL.ID = ?
        """;

        PreparedStatementCreator preparedStatementGet = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, idClient);
            return preparedStatement;
        };

        RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble("price"));
            int quantity = resultSet.getInt("count");
            return new Product(id, name, price, quantity);
        };

        return  jdbcTemplate.query( preparedStatementGet,productRowMapper);

    }

    @Override
    public boolean deleteById(long idClient) {
        var deleteSql1 = """
        DELETE FROM products_vanchugova_da.PRODUCT_CLIENT
        WHERE ID_CART=(SELECT CART_ID FROM CLIENT WHERE ID=?);
        """;
        var deleteSql2 = """
        DELETE FROM products_vanchugova_da.CART 
        WHERE ID=?;
        """;
        var deleteSql3 = """
        DELETE FROM products_vanchugova_da.CLIENT
        WHERE ID=?
        """;

        int row3 = jdbcTemplate.update(deleteSql3,idClient);
        int row2 = jdbcTemplate.update(deleteSql2,idClient);
        int row1 = jdbcTemplate.update(deleteSql1,idClient);

        return row2>0 && row3>0;
    }
}



