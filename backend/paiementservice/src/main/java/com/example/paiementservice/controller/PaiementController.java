package com.example.paiementservice.controller;

import com.example.paiementservice.dto.PaiementRequest;
import com.example.paiementservice.dto.PaiementResponse;
import com.example.paiementservice.service.PaiementService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @GetMapping("/")
    public String home() {
        return "Paiement Service is running!";
    }

    @GetMapping
    @Retry(name = "paiementRetry", fallbackMethod = "fallbackPaiementsCB")
    @CircuitBreaker(name = "paiementCB", fallbackMethod = "fallbackPaiementsCB")
    public ResponseEntity<List<PaiementResponse>> getAllPaiements() {
        List<PaiementResponse> paiements = paiementService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementResponse> getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/facture/{factureId}")
    public ResponseEntity<List<PaiementResponse>> getPaiementsByFactureId(@PathVariable Long factureId) {
        List<PaiementResponse> paiements = paiementService.getPaiementsByFactureId(factureId);
        return ResponseEntity.ok(paiements);
    }

    @PostMapping
    public ResponseEntity<PaiementResponse> processPaiement(@Valid @RequestBody PaiementRequest request) {
        PaiementResponse created = paiementService.processPaiement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    public ResponseEntity<List<PaiementResponse>> fallbackPaiementsCB(Exception e) {
        System.err.println("Paiement Service Fallback: " + e.getMessage());
        return ResponseEntity.ok(List.of());
    }
}

