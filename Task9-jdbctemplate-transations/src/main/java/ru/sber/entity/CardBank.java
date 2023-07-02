package ru.sber.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CardBank {
    private long id;
    private BigDecimal account;
}
