package ru.sber.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Клиент сайта
 */



@Data
@AllArgsConstructor
public class ClientRec {
    /**
     * @param id уникальный идентификатор клиента
     */
    private long id;
    /**
     * @param name имя клиентов
     */
    private String name;
    /**
     * @param cart Корзина клиента
     */
    private Cart cart;

    public ClientRec(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.cart = client.getCart();
    }
}
