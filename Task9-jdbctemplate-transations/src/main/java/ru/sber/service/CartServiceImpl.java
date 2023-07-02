package ru.sber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.repository.CartRepository;

import java.math.BigDecimal;
@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public boolean save(long idCart, long idProduct, Integer quantity) {
        return cartRepository.addProductFromCartById(idCart,idProduct, quantity);
    }

    @Override
    public boolean update(long idCart, long idProduct, Integer quantity) {
        return cartRepository.changeQuantity( idCart,idProduct, quantity);
    }

    @Override
    public boolean deleteById(long idCart, long idProduct) {
        return cartRepository.deleteProductFromCartById(idCart, idProduct);
    }

    @Override
    public BigDecimal payment(long idCart, long idCard) {
        return cartRepository.payment(idCart,idCard);
    }
}
