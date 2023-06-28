package ru.sber.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * Корзина товаров к приобретению

 */

@Data
@AllArgsConstructor
public class ShoppingCard {
    /**
     * @param id уникальный идентификатор корзины товаров
     */
    private long id;
    /**
     * @param  productsList список продуктов
     */
    private List<Product> productsList;
    /**
     * @param promoсode промокод
     */
    private String promoсode;
}
