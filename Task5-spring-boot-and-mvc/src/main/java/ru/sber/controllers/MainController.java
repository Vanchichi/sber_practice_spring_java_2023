package ru.sber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер (P.S. Кирилл, прости за плохой JavaDoc)
 */
@Controller
public class MainController {
    @GetMapping("/home")
    public String mainPage(){
        return "evangelion.html";
    }
}
