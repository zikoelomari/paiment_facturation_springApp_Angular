package com.example.factureservice.controller;

import com.example.factureservice.dto.FactureRequest;
import com.example.factureservice.dto.FactureResponse;
import com.example.factureservice.dto.FactureUpdate;
import com.example.factureservice.service.FactureService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;

    @GetMapping("/")
    public String home() {
        return "Facture Service is running!";
    }

    @GetMapping
    @Retry(name = "factureRetry", fallbackMethod = "fallbackFacturesCB")
    @CircuitBreaker(name = "factureCB", fallbackMethod = "fallbackFacturesCB")
    public ResponseEntity<List<FactureResponse>> getAllFactures() {
        List<FactureResponse> factures = factureService.getAllFactures();
        return ResponseEntity.ok(factures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactureResponse> getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<FactureResponse> getFactureByNumero(@PathVariable String numero) {
        return factureService.getFactureByNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<FactureResponse>> getFacturesByClientId(@PathVariable Long clientId) {
        List<FactureResponse> factures = factureService.getFacturesByClientId(clientId);
        return ResponseEntity.ok(factures);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FactureResponse>> getFacturesByStatus(@PathVariable String status) {
        List<FactureResponse> factures = factureService.getFacturesByStatut(status);
        return ResponseEntity.ok(factures);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<FactureResponse>> getOverdueFactures() {
        List<FactureResponse> factures = factureService.getOverdueFactures();
        return ResponseEntity.ok(factures);
    }

    @PostMapping
    public ResponseEntity<FactureResponse> createFacture(@Valid @RequestBody FactureRequest request) {
        FactureResponse created = factureService.createFacture(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FactureResponse> updateFacture(@PathVariable Long id, @Valid @RequestBody FactureUpdate update) {
        return factureService.updateFacture(id, update)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/paid")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        factureService.markAsPaid(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<FactureResponse>> fallbackFacturesCB(Exception e) {
        System.err.println("Facture Service Fallback: " + e.getMessage());
        return ResponseEntity.ok(List.of());
    }
}
