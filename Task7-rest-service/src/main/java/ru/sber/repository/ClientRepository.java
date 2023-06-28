package ru.sber.repository;

import ru.sber.entity.Client;


import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    long saveClient(Client client);

    Optional<Client> findClientById(long id);

    List<Client> findAllClient(String name);

    boolean deleteClientById(long id);
}
