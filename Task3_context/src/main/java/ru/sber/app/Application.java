package ru.sber.app;
import ru.sber.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.proxies.TransferByPhoneApp;
import ru.sber.repositories.AppPaymentsRepository;

import java.math.BigDecimal;


public class Application {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        TransferByPhoneApp transfer_1 = context.getBean(TransferByPhoneApp.class);
        TransferByPhoneApp transfer_2 = context.getBean(TransferByPhoneApp.class);
        TransferByPhoneApp transfer_3 = context.getBean(TransferByPhoneApp.class);
        AppPaymentsRepository repository = context.getBean(AppPaymentsRepository.class);
        //оплата у существующего клиента
        transfer_1.transaction("89114412112", BigDecimal.valueOf(1000));
        //оплата у не существующего клиента
        transfer_2.transaction("89114412121", BigDecimal.valueOf(1000));
        //оплата у существующего клиента
        transfer_3.transaction("89456578998", BigDecimal.valueOf(700));

        repository.getPaymentsList();

    }
}
