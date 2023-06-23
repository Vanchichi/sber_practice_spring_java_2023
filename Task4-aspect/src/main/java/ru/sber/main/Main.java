package ru.sber.main;

import ru.sber.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.services.ShoppingListService;
import java.util.List;

/**
 * Класс для тестирования функций и аннотаций
 */
public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        ShoppingListService shoppingListService = context.getBean(ShoppingListService.class);

        shoppingListService.findStoreWithoutAnnotation("Maksi");
        shoppingListService.findStore("Magnit");
        shoppingListService.findSum(350);
        shoppingListService.findListProducts(List.of("milk","bread"));

       shoppingListService.findStoreWithoutAnnotation("");
       shoppingListService.findStore("");
       shoppingListService.findSum(null);
       shoppingListService.findListProducts(List.of());
    }
}
