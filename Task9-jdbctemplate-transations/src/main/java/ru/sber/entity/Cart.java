package ru.sber.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sber.entity.Product;

import java.util.List;

/**
 * Корзина товаров к приобретению

 */


@Data
@AllArgsConstructor
public class Cart {
    /**
     * @param id уникальный идентификатор корзины товаров
     */
    private long id;
    /**
     * @param  productsList список продуктов
     */
    private List<Product> productsList;
    /**
     * @param promoCode промокод
     */
    private String promoCode;
}
