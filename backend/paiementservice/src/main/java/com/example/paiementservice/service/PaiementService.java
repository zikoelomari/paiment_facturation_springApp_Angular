package com.example.paiementservice.service;

import com.example.paiementservice.dto.PaiementRequest;
import com.example.paiementservice.dto.PaiementResponse;

import java.util.List;
import java.util.Optional;

public interface PaiementService {
    List<PaiementResponse> getAllPaiements();
    Optional<PaiementResponse> getPaiementById(Long id);
    List<PaiementResponse> getPaiementsByFactureId(Long factureId);
    PaiementResponse processPaiement(PaiementRequest request);
}

