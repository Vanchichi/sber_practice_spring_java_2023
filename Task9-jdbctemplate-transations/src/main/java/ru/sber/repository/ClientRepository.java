package ru.sber.repository;

import ru.sber.entity.Client;


import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    long save(Client client);

    Optional<Client> findById(long id);

    boolean deleteById(long id);
}
