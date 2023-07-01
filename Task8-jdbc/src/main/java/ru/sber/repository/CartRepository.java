package ru.sber.repository;


import ru.sber.entity.Cart;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartRepository {
   // Optional<Cart> findCartById(long id);
    boolean addProductFromCartById(long idShoppingCard, long idProduct, Integer quantity);
    boolean changeQuantity(long idShoppingCard, long idProduct,Integer quantity);
    boolean deleteProductFromCartById(long idShoppingCard, long idProduct);

   BigDecimal payment(long idCart, long idCard);
}
