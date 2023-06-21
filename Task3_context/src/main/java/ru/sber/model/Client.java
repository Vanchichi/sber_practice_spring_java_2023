package ru.sber.model;
import org.springframework.stereotype.Component;


public class Client {
    private String nameClient;
    private String numberPhone;

    public Client(String nameClient, String  numberPhone) {
        this.nameClient = nameClient;
        this.numberPhone = numberPhone;
    }

    public String getNumber() {
        return numberPhone;
    }

}
