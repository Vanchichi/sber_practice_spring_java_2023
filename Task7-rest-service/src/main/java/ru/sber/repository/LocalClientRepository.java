package ru.sber.repository;

import ru.sber.entity.Client;
import org.springframework.stereotype.Repository;
import ru.sber.entity.ShoppingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class LocalClientRepository implements ClientRepository{


    private List<Client> clients = new ArrayList<>(List.of(
            new Client(1,"Daria","Vanchichi","123123123",new ShoppingCard(generateIdClient() ,new ArrayList<>(),"hjgj")),
            new Client(2,"Maria","Marusya","145453",new ShoppingCard(generateIdClient(),new ArrayList<>(),"ghnnvb")),
            new Client(3,"Valeria","Lerusik","435304332",new ShoppingCard(generateIdClient(),new ArrayList<>(),"vbfd"))
            ));
    @Override
    public long saveClient(Client client) {
        long id = generateIdClient();
        client.setId(id);
        client.setShoppingCard(new ShoppingCard(generateIdClient() ,new ArrayList<>(),"hjgj"));
        clients.add(client);
        return id;
    }

    @Override
    public Optional<Client> findClientById(long id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findAny();
    }

    @Override
    public List<Client> findAllClient(String name) {
        if (name == null) {
            return clients;
        }

        return clients.stream()
                .filter(client -> client.getName().equals(name))
                .toList();
    }

    @Override
    public boolean deleteClientById(long id) {
        return clients.removeIf(client -> client.getId() == id);
    }

    private long generateIdClient() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}
