package ru.sber.sevices;

import org.springframework.stereotype.Service;
import ru.sber.model.Client;
import ru.sber.repositories.AppClientsRepository;
import java.util.List;


@Service
public class BankClientsApp {

    private final AppClientsRepository appClientsRepository = new AppClientsRepository();
    private List<Client> clients = appClientsRepository.getClients();

    public boolean checkClient (String numberPhone){
        for(Client client : clients){
            if(client.getNumber().equals(numberPhone)){
                return true;
            }
        }
        return false;
    }
}
