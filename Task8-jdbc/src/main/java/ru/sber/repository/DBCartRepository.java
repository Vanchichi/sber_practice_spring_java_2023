package ru.sber.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.sber.entity.Cart;
import ru.sber.entity.Client;
import ru.sber.entity.Product;
import ru.sber.proxy.ClientBankProxy;

@Repository
public class DBCartRepository implements CartRepository {

    private ClientBankProxy bankProxy;
    @Autowired
    public DBCartRepository(ClientBankProxy bankProxy) {
        this.bankProxy = bankProxy;
    }

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";
    /*@Override
    public Optional<Cart> findCartById(long idCart) {
        var selectSql1 = """
                SELECT PROMOCODE FROM CART
                WHERE ID=?
        """;
        var selectSql2 = """
                SELECT PC.ID_PRODUCT,P.NAME, P.PRICE, PC.COUNT
                FROM PRODUCT_CLIENT PC
                JOIN PRODUCT P
                ON PC.ID_PRODUCT=P.ID
                WHERE PC.ID_CART=?
                """;
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement1 = connection.prepareStatement(selectSql1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(selectSql2))
        {
            preparedStatement1.setLong(1, idCart);
            preparedStatement2.setLong(1, idCart);

            List<Product> productsList = null;
            Cart cart;

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (resultSet1.next()) {
                String promocode = resultSet1.getString("promocode");

                ResultSet resultSet2 = preparedStatement1.executeQuery();
                while (resultSet2.next()){
                    long idProduct = resultSet2.getLong("id_product");
                    String nameProduct = resultSet2.getString("name");
                    BigDecimal priceProduct = resultSet2.getBigDecimal("price");
                    Integer quantity = resultSet2.getInt("count");
                    productsList.add(new Product(idProduct,nameProduct,priceProduct,quantity));
                }
                cart = new Cart(idCart,productsList,promocode);
                return Optional.of(cart);
            }
            return Optional.empty();
        }catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }*/

    @Override
    public boolean addProductFromCartById(long idCart, long idProduct,Integer quantity) {
        var insertSql = """
                INSERT INTO products_vanchugova_da.PRODUCT_CLIENT
                (ID_CART, ID_PRODUCT, COUNT)
                VALUES 
                (?,?,?)
                """;
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql))
        {
            preparedStatement.setLong(1, idCart);
            preparedStatement.setLong(2, idProduct);
            preparedStatement.setInt(3,quantity);

            var rows = preparedStatement.executeUpdate();

            if (rows>0) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean changeQuantity(long idCart, long idProduct, Integer quantity) {
        var updateSql1 = """
                UPDATE  products_vanchugova_da.PRODUCT_CLIENT
                SET COUNT=?
                WHERE ID_CART=?
                """;
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement1 = connection.prepareStatement(updateSql1))
             {
            preparedStatement1.setInt(1, quantity);
            preparedStatement1.setLong(2, idCart);
            var rows = preparedStatement1.executeUpdate();
                 if(rows > 0){
                     return true;
                 }
                 return false;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProductFromCartById(long idCart, long idProduct) {
        var deleteSql = """
                DELETE FROM products_vanchugova_da.PRODUCT_CLIENT
                WHERE ((ID_CART = ?) AND (ID_PRODUCT = ?))
                """;
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, idCart);
            preparedStatement.setLong(2, idProduct);

            var rows = preparedStatement.executeUpdate();

            if(rows > 0){
                return true;
            }
            return false;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal payment(long idCart,long idCard) {
     var selectSql = """
             SELECT SUM (P.PRICE*PC.COUNT) SUMMA
             FROM products_vanchugova_da.PRODUCT P
             JOIN products_vanchugova_da.PRODUCT_CLIENT PC
             ON P.ID=PC.ID_PRODUCT
             WHERE PC.ID_CART = ?
             """;
        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            preparedStatement.setLong(1, idCart);

            BigDecimal account = bankProxy.getAccountByIdCard(idCard);
            BigDecimal sum = null;

            var rs = preparedStatement.executeQuery();

            if(rs.next()){
                sum=rs.getBigDecimal("SUMMA");
            }


            if(account.compareTo(sum)<0){
                return BigDecimal.valueOf(0);
            }else {
                return BigDecimal.valueOf(account.compareTo(sum));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
