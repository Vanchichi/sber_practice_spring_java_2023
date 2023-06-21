package ru.sber.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;




public class Payment {

    private String numberPhone;

    private BigDecimal sum;

    public Payment(String numberPhone, BigDecimal sum) {
        this.numberPhone = numberPhone;
        this.sum = sum;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public BigDecimal getSum() {
        return sum;
    }
}
