package ru.sber.proxies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.sber.sevices.BankClientsApp;
import ru.sber.repositories.AppPaymentsRepository;
import ru.sber.model.Payment;
import java.math.BigDecimal;


@Scope("prototype")
@Component
public class TransferByPhoneApp {
    private final BankClientsApp bankClientsApp = new BankClientsApp();

    private final AppPaymentsRepository appPaymentsRepository;
    @Autowired
    public TransferByPhoneApp(AppPaymentsRepository appPaymentsRepository) {
        this.appPaymentsRepository = appPaymentsRepository;
    }

    public void transaction(String numberPhone, BigDecimal sum) {
        if (bankClientsApp.checkClient(numberPhone)) {
            Payment payment = new Payment(numberPhone,sum);
            appPaymentsRepository.setPayments(payment);
            System.out.println("Payment was successful");
        } else {
            System.out.println("Error: Client not exist");
        }
    }
}