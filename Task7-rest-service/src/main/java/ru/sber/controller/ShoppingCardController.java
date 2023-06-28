package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import ru.sber.repository.ShoppingCardRepository;

@Slf4j
@RestController
@RequestMapping("shoppingCards")
public class ShoppingCardController {
    private ShoppingCardRepository shoppingCardRepository;
    @Autowired
    public ShoppingCardController(ShoppingCardRepository shoppingCardRepository) {
        this.shoppingCardRepository=shoppingCardRepository;
    }

    @PostMapping("/{idShoppingCard}/products/{idProduct}")
    public ResponseEntity<?> addProductInShoppingCard( @PathVariable long idShoppingCard,  @PathVariable long idProduct) {
        log.info("Добавление товара с id= {} в корзине с id {}", idProduct, idShoppingCard);
        boolean isAdd= shoppingCardRepository.addProductFromShoppingCardById(idShoppingCard, idProduct);
        if(isAdd){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idShoppingCard}/products/{idProduct}")
    public ResponseEntity<?> deleteProductInShoppingCard(@PathVariable long idShoppingCard,@PathVariable long idProduct) {
        log.info("Удаление товара с id= {} в корзине с id {}", idProduct, idShoppingCard);
        boolean isDeleted= shoppingCardRepository.deleteProductFromShoppingCardById(idShoppingCard, idProduct);
        if (isDeleted) {
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idShoppingCard}/products/{idProduct}/{quantity}")
    public ResponseEntity<?>  updateQuantityProductInShoppingCard( @PathVariable long idShoppingCard, @PathVariable long idProduct,@PathVariable Integer quantity) {
        log.info("Изменение количества товара с id= {}. в корзину с id {}",idShoppingCard, idProduct,quantity);
        boolean isChangedQuantity = shoppingCardRepository.changeQuantity(idShoppingCard, idProduct,quantity);
        if (isChangedQuantity) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping ("/{idShoppingCard}")
    public ResponseEntity<?> payment(@PathVariable long idShoppingCard) {
        log.info("Недостаточно средств для оплаты в корзине с id{}",idShoppingCard);
        boolean  isPaid = shoppingCardRepository.payment(idShoppingCard);
        if (isPaid) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
