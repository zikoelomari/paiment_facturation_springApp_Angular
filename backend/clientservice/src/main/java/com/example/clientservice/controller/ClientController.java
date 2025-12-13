package com.example.clientservice.controller;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.dto.ClientUpdate;
import com.example.clientservice.service.ClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/")
    public String home() {
        return "Client Service is running!";
    }

    @GetMapping
    @Retry(name = "clientRetry", fallbackMethod = "fallbackClientsCB")
    @CircuitBreaker(name = "clientCB", fallbackMethod = "fallbackClientsCB")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientResponse> getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<ClientResponse>> searchClientsByName(@RequestParam String name) {
        List<ClientResponse> clients = clientService.searchClientsByName(name);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<ClientResponse>> searchClientsByEmail(@RequestParam String email) {
        List<ClientResponse> clients = clientService.searchClientsByEmail(email);
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        ClientResponse created = clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdate update) {
        return clientService.updateClient(id, update)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/last-login")
    public ResponseEntity<Void> updateLastLogin(@PathVariable Long id) {
        clientService.updateLastLogin(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<ClientResponse>> fallbackClientsCB(Exception e) {
        System.err.println("Client Service Fallback: " + e.getMessage());
        return ResponseEntity.ok(List.of());
    }
}

