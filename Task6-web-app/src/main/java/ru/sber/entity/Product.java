package ru.sber.entity;

import java.math.BigDecimal;

/**
 * Товары по Евангелиону, которые продают на сайте
 * @param name наименование товара
 * @param URL ссылка для отображения фото товара
 * @param price стоимость товара
 */

public record Product(String name, String URL, BigDecimal price) {

}
