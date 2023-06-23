package ru.sber.entity;

import java.util.List;

/**
 * Класс Список покупок
 */

public class ShoppingList {
    private String nameStore;
    private Integer sum;
    private List<String> listProducts;


    public ShoppingList(String nameStore, Integer sum, List<String> listProducts) {
        this.nameStore = nameStore;
        this.sum = sum;
        this.listProducts = listProducts;
    }

    public String getNameStore() {
        return nameStore;
    }

    public Integer getSum() {
        return sum;
    }

    public List<String> getListProducts() {
        return listProducts;
    }

}
