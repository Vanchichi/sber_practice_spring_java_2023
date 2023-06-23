package ru.sber.services;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import ru.sber.aspects.NotEmpty;
import ru.sber.entity.ShoppingList;
import ru.sber.repositories.RepositoryShoppingList;

/**
 * Сервис для поиска различной информации в списках покупок
 */
@Service
public class ShoppingListService {
    private final RepositoryShoppingList repositoryShoppingList = new RepositoryShoppingList();
    private final List<ShoppingList> shoppingLists = repositoryShoppingList.getShoppingLists();
    private Logger logger = Logger.getLogger(ShoppingListService.class.getName());

    @NotEmpty
    public String findStore(String nameStore) {
        boolean found = shoppingLists.stream()
                .anyMatch(shoppinglist -> shoppinglist.getNameStore().equals(nameStore));

        if (found) {
            logger.info("nameStore: " + nameStore + " was found");
        } else {
            logger.info("nameStore not found");
        }

        return "SUCCESS";
    }

    @NotEmpty
    public String findSum(Integer sum) {
        boolean found = shoppingLists.stream()
                .anyMatch(shoppinglist -> shoppinglist.getSum().equals(sum));

        if (found) {
            logger.info("sum: " + sum + " was found");
        } else {
            logger.info("sum not found");
        }

        return "SUCCESS";
    }

    @NotEmpty
    public String findListProducts(List<String> listProducts) {
        boolean found = shoppingLists.stream()
                .anyMatch(shoppinglist -> shoppinglist.getListProducts().equals(listProducts));

        if (found) {
            logger.info("listProducts: " + listProducts + " was found");
        } else {
            logger.info("listProducts not found");
        }

        return "SUCCESS";
    }

    public String findStoreWithoutAnnotation(String nameStore) {
        boolean found = shoppingLists.stream()
                .anyMatch(shoppinglist -> shoppinglist.getNameStore().equals(nameStore));

        if (found) {
            logger.info("nameStore: " + nameStore + " was found");
        } else {
            logger.info("nameStore not found");
        }

        return "SUCCESS";
    }
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

}
