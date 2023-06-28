package ru.sber.repository;


import ru.sber.entity.ShoppingCard;
import java.util.List;
import java.util.Optional;

public interface ShoppingCardRepository {
    long saveShoppingCard(ShoppingCard shoppingCard);
    Optional<ShoppingCard> findShoppingById(long id);
    ShoppingCard getShoppingCardById(long id);
    List<ShoppingCard> findAllPShoppingCard(String name);
    boolean addProductFromShoppingCardById(long idShoppingCard, long idProduct);
    boolean changeQuantity(long idShoppingCard, long idProduct,Integer quantity);
    boolean deleteProductFromShoppingCardById(long idShoppingCard, long idProduct);

    boolean payment(

    );
}
