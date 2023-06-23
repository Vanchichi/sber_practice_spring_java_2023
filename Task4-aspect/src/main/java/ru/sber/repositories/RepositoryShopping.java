package ru.sber.repositories;

import ru.sber.entity.ShoppingList;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Интерфейс репозитория, хранящего информацию о списках покупках
 */

@Repository
public interface RepositoryShopping {
    public List<ShoppingList> getShoppingLists();
}
