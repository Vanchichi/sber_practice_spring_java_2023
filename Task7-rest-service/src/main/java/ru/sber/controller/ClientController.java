package ru.sber.controller;

import ru.sber.entity.Client;
import ru.sber.entity.Product;
import ru.sber.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {
    private ClientRepository clientRepository;
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public long addClient(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);

        return clientRepository.saveClient(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClients(@PathVariable long id) {
        Optional<Client> client = clientRepository.findClientById(id);
        log.info("Получение клиента по id {}",client);
        if (client.isPresent()) {
            return ResponseEntity.ok().body(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = clientRepository.deleteClientById(id);
        log.info("Удаление клиента по id {}");
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
