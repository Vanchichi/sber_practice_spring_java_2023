package ru.sber.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Товары, которые продают на сайте
 */


@Data
@AllArgsConstructor
public class Product {
    /**
     *@param id уникальный идентификатор товара
     *
     */
    private long id;
    /**
     * @param name наименование товара
     */
    private String name;
    /**
     * @param price цена товара
     */
    private BigDecimal price;
    /**
     * @param quantity количество товара
     */
    private Integer quantity;

}
