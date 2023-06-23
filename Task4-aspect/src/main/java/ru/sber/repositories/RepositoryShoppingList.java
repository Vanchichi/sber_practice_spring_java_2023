package ru.sber.repositories;
import org.springframework.stereotype.Repository;
import ru.sber.entity.ShoppingList;
import java.util.List;

/**
 * Класс репозитория, хранящего информацию о списках покупках
 */
@Repository
public class RepositoryShoppingList implements RepositoryShopping {

    @Override
    public List<ShoppingList> getShoppingLists() {

        return List.of(
                new ShoppingList("Maksi",200, List.of("milk","bread")),
                new ShoppingList("Magnit",350, List.of("banana","carrots")),
                new ShoppingList("Pyaterochka",1000,List.of("chicken","Potato"))
        );

    }
}
