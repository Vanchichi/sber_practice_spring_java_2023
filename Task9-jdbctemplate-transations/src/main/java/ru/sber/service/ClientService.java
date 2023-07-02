package ru.sber.service;

import ru.sber.entity.Client;

import java.util.Optional;

public interface ClientService {
    long save(Client client);

    Optional<Client> findById(long id);

    boolean deleteById(long id);
}
