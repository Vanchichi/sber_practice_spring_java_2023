package ru.sber.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Клиент сайта
 */

@Data
@AllArgsConstructor
public class Client {
    /**
     * @param id уникальный идентификатор клиента
     */
    private long id;
    /**
     * @param name имя клиентов
     */
    private String name;
    /**
     * @param login логин клиента на сайте
     */
    private String login;
    /**
     * @param password пароль клиента к аккаунту
     */
    private String password;
    /**
     * @param shoppingCard Корзина клиента
     */
    private ShoppingCard shoppingCard;
}
