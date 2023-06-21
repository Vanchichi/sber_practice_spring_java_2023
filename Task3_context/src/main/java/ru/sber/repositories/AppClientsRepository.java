package ru.sber.repositories;

import ru.sber.model.Client;


import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AppClientsRepository {
    public List<Client> getClients() {
        System.out.println("Getting a list of clients from DB");

        return List.of(
                new Client("Daria","89114412112"),
                new Client("Kseniya","89456578998"),
                new Client("Viktoria","89215679867")
        );
    }


}
