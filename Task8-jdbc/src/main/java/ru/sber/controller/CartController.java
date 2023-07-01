package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import ru.sber.repository.CartRepository;

import java.math.BigDecimal;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("carts")
public class CartController {
    private CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository=cartRepository;
    }

    @PostMapping("/{idCart}/products/{idProduct}/{quantity}")
    public ResponseEntity<?> addProductInCart(@PathVariable long idCart,  @PathVariable long idProduct, @PathVariable Integer quantity) {
        log.info("Добавление товара с id= {} в корзине с id {}", idProduct, idCart);
        return ResponseEntity.created(URI.create("Cart/"+cartRepository.addProductFromCartById(idCart, idProduct, quantity))).build();
    }
    @PutMapping("/{idCart}/products/{idProduct}/quantity/{quantity}")
    public ResponseEntity<?>  updateQuantityProductInCart(@PathVariable long idCart,  @PathVariable long idProduct, @PathVariable Integer quantity) {
        log.info("Изменение количества товара с id= {}. в корзину с id {}",idCart, idProduct,quantity);
        boolean isChangedQuantity = cartRepository.changeQuantity(idCart, idProduct,quantity);
        if (isChangedQuantity) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{idCart}/products/{idProduct}")
    public ResponseEntity<?> deleteProductInCart(@PathVariable long idCart,@PathVariable long idProduct) {
        log.info("Удаление товара с id= {} в корзине с id {}", idProduct, idCart);
        boolean isDeleted= cartRepository.deleteProductFromCartById(idCart, idProduct);
        if (isDeleted) {
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping ("/{idCart}/payment/{idCard}")
    public ResponseEntity<?> payment(@PathVariable long idCart,@PathVariable long idCard) {
        log.info("Оплаты в корзине с id{}",idCart);
        BigDecimal isPaid = cartRepository.payment(idCart,idCard);
        if (isPaid != BigDecimal.valueOf(0)) {
            log.info("Сумма к оплате",isPaid);
            return ResponseEntity.ok().build();
        } else {
            log.info("Недостаточно средств");
            return ResponseEntity.notFound().build();
        }
    }
}
