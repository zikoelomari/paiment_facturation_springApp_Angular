package com.example.factureservice.service;

import com.example.factureservice.dto.FactureRequest;
import com.example.factureservice.dto.FactureResponse;
import com.example.factureservice.dto.FactureUpdate;

import java.util.List;
import java.util.Optional;

public interface FactureService {
    List<FactureResponse> getAllFactures();
    Optional<FactureResponse> getFactureById(Long id);
    Optional<FactureResponse> getFactureByNumero(String numero);
    List<FactureResponse> getFacturesByClientId(Long clientId);
    List<FactureResponse> getFacturesByStatut(String statut);
    FactureResponse createFacture(FactureRequest request);
    Optional<FactureResponse> updateFacture(Long id, FactureUpdate update);
    void deleteFacture(Long id);
    void markAsPaid(Long id);
    List<FactureResponse> getOverdueFactures();
}

