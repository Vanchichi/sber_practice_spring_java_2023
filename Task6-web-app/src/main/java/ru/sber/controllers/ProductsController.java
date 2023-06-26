package ru.sber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import ru.sber.services.ProductService;
import ru.sber.entity.Product;

import java.math.BigDecimal;

/**
 * Контроллер возвращает основную страницу. Реализован функционал добавления товара, отображения товара.
 */
@Controller
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/evangelion")
    public String viewProducts(Model model) {
        var products = productService.findAll();
        model.addAttribute("products", products);

        return "evangelion.html";
    }

    @PostMapping("/evangelion")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String URL,
            @RequestParam BigDecimal price,
            Model model
    ) {
        Product p = new Product(name,URL,price);

        productService.addProduct(p);

        var products = productService.findAll();
        model.addAttribute("products", products);

        return "evangelion.html";
    }
}
