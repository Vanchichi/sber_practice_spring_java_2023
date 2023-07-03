package ru.sber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sber.entity.Client;
import ru.sber.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public long addClient(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);

        return clientService.save(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClients(@PathVariable long id) {
        Optional<Client> client = clientService.findById(id);
        log.info("Получение клиента по id {}",client);
        if (client.isPresent()) {
            return ResponseEntity.ok().body(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted =clientService.deleteById(id);
        log.info("Удаление клиента по id {}");
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}