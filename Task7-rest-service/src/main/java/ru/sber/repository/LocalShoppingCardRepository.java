package ru.sber.repository;
import ru.sber.entity.Product;
import ru.sber.entity.ShoppingCard;
import ru.sber.repository.LocalProductRepository;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public class LocalShoppingCardRepository implements ShoppingCardRepository {
    private  LocalProductRepository localProductRepository;
    private List<ShoppingCard> shoppingCards;

    public LocalShoppingCardRepository(LocalProductRepository localProductRepository) {
        this.localProductRepository = localProductRepository;
        this.shoppingCards = new ArrayList<>(List.of(
                new ShoppingCard(1, new ArrayList<>(List.of(
                        (localProductRepository.getProductById(1)),
                        (localProductRepository.getProductById(2)))
                ), "asdasd"),

                new ShoppingCard(2, new ArrayList<>(List.of(
                        (localProductRepository.getProductById(2)),
                        (localProductRepository.getProductById(1)))
                ), "dfd")
        ));
    }


    @Override
    public long saveShoppingCard(ShoppingCard shoppingCard) {
        return 0;
    }

    @Override
    public Optional<ShoppingCard> findShoppingById(long id) {
        return Optional.empty();
    }

    @Override
    public ShoppingCard getShoppingCardById(long id) {
        return null;
    }

    @Override
    public List<ShoppingCard> findAllPShoppingCard(String name) {
        return null;
    }

    @Override
    public boolean addProductFromShoppingCardById(long idShoppingCard, long idProduct) {
        ShoppingCard shoppingCard =shoppingCards.get((int) idShoppingCard);
        if (shoppingCard == null) {
            return false;
        }
        shoppingCard.getProductsList().add(localProductRepository.getProductById(idProduct));
        return true;
    }

    @Override
    public boolean changeQuantity(long idShoppingCard, long idProduct, Integer quantity) {
        ShoppingCard shoppingCard =shoppingCards.get((int) idShoppingCard);
        if (shoppingCard == null && localProductRepository.getProductById(idProduct) == null) {
            return false;
        }

        Product product = localProductRepository.getProductById(idProduct);
        product.setQuantity(quantity);
        return true;
    }


    @Override
    public boolean deleteProductFromShoppingCardById(long idShoppingCard, long idProduct) {
        ShoppingCard shoppingCard =shoppingCards.get((int) idShoppingCard);
        if (shoppingCard == null || localProductRepository.getProductById(idProduct) == null) {
            return false;
        }
        return shoppingCard.getProductsList().removeIf(product -> product.getId() == idProduct);
    }

    @Override
    public boolean payment(long idShoppingCard) {
        return false;
    }

    private long generateIdShoppingCard() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}