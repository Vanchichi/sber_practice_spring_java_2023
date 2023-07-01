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

@Repository
public class DBClientRepository implements ClientRepository {
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    @Override
    public long saveClient(Client client) {
        var insertSql1 = "INSERT INTO products_vanchugova_da.CLIENT (name,username,password,cart_id) VALUES (?,?,?,?);";
        var insertSql2 = "INSERT INTO products_vanchugova_da.CART (promocode) VALUES (?)";
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement1 = connection.prepareStatement(insertSql1, Statement.RETURN_GENERATED_KEYS);
             var prepareStatement2 = connection.prepareStatement(insertSql2, Statement.RETURN_GENERATED_KEYS)
        ) {
            prepareStatement2.setString(1,"NEW50");
            prepareStatement2.executeUpdate();
            ResultSet rs2 = prepareStatement2.getGeneratedKeys();
            long idCart;
            if (rs2.next()) {
                idCart = rs2.getLong(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора корзины");
            }

            prepareStatement1.setString(1,client.getName());
            prepareStatement1.setString(2, client.getLogin());
            prepareStatement1.setString(3,client.getPassword());
            prepareStatement1.setLong(4,  idCart);

            prepareStatement1.executeUpdate();

            ResultSet rs1 = prepareStatement1.getGeneratedKeys();
            long idClient;

            if (rs1.next()) {
                idClient=rs2.getLong(1);

            } else {
                throw new RuntimeException("Ошибка при получении идентификатора клиента");
            }
            return idClient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> findClientById(long idClient) {
        String selectSql2 = """
            SELECT CL.NAME, CL.USERNAME, CL.PASSWORD, CL.CART_ID, CA.PROMOCODE
            FROM products_vanchugova_da.CLIENT CL
            JOIN products_vanchugova_da.CART CA ON CL.CART_ID = CA.ID
            WHERE CL.ID = ?
        """;
        String selectSql1 = """
            SELECT P.ID, P.NAME, P.PRICE, PC.COUNT
            FROM products_vanchugova_da.CLIENT CL
            JOIN products_vanchugova_da.CART CA ON CL.CART_ID = CA.ID
            JOIN products_vanchugova_da.PRODUCT_CLIENT PC ON CA.ID = PC.ID_CART
            JOIN products_vanchugova_da.PRODUCT P ON PC.ID_PRODUCT = P.ID
            WHERE CL.ID = ?
        """;

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectSql1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(selectSql2)) {

            preparedStatement2.setLong(1, idClient);
            preparedStatement1.setLong(1, idClient);

            List<Product> productList = new ArrayList<>();
            Client client;
            Cart cart;

            ResultSet resultSet2 = preparedStatement2.executeQuery();

            if (resultSet2.next()) {
                String nameClient = resultSet2.getString("name");
                String login = resultSet2.getString("username");
                String password = resultSet2.getString("password");
                long idCart = resultSet2.getLong("cart_id");
                String promoCode = resultSet2.getString("promocode");

                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    long idProduct = resultSet1.getLong("id");
                    String nameProduct = resultSet1.getString("name");
                    BigDecimal price = resultSet1.getBigDecimal("price");
                    int quantity = resultSet1.getInt("count");
                    Product product = new Product(idProduct, nameProduct, price, quantity);
                    productList.add(product);
                }
                cart = new Cart(idCart, productList, promoCode);
                client = new Client(idClient, nameClient, login, password, cart);
                return Optional.of(client);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteClientById(long idClient) {
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
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement1 = connection.prepareStatement(deleteSql1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(deleteSql2);
             PreparedStatement preparedStatement3 = connection.prepareStatement(deleteSql3))
        {
            preparedStatement1.setLong(1, idClient);
            preparedStatement3.setLong(1, idClient);
            var rows3 = preparedStatement3.executeUpdate();
            preparedStatement2.setLong(1, idClient);
            var rows2 = preparedStatement2.executeUpdate();

            if( rows3>0){
                return true;
            }

            return false;
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}



