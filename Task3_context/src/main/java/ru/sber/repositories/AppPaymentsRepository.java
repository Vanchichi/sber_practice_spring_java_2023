package ru.sber.repositories;


import ru.sber.model.Payment;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AppPaymentsRepository  {

    List<Payment> payments = new ArrayList<>();

    public void setPayments(Payment payment) {
        payments.add(payment);
    }

    public List<Payment> getPaymentsList() {
        System.out.println("Payment perfection: ");
        for (Payment payment : payments) {
            System.out.println("NumberPhone: "+payment.getNumberPhone()+", summa: "+payment.getSum());
        }
        return payments;
    }
}
