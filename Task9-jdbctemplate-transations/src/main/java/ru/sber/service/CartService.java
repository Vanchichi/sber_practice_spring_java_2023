package ru.sber.service;

import java.math.BigDecimal;

public interface CartService {
    boolean save(long idCart, long idProduct, Integer quantity);
    boolean update(long idCart, long idProduct,Integer quantity);
    boolean deleteById(long idCart, long idProduct);

    BigDecimal payment(long idCart, long idCard);
}
