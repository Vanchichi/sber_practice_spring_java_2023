package ru.sber.proxy;

import org.springframework.stereotype.Service;
import ru.sber.entity.CardBank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class ClientBankProxy {
        List<CardBank> cardBankList= new ArrayList<>(List.of(
                new CardBank(1, BigDecimal.valueOf(2000000)),
               new CardBank(2,BigDecimal.valueOf(100000)),
               new CardBank(3,BigDecimal.valueOf(1000))
            ));

        public BigDecimal getAccountByIdCard (long idCard) {
            for (CardBank cardBank : cardBankList) {
                if (cardBank.getId() == idCard) {
                    return cardBank.getAccount();
                }
            }
            throw new RuntimeException("Нет такой карты");
        }

    public BigDecimal changeAccount (long idCard,BigDecimal summa) {
        for (CardBank cardBank : cardBankList) {
            if (cardBank.getId() == idCard) {
                cardBank.setAccount(cardBank.getAccount().subtract(summa));
            }
        }
        throw new RuntimeException("Нет такой карты");
    }
}
