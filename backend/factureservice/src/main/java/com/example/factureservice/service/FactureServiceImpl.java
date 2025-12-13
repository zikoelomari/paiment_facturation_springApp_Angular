package com.example.factureservice.service;

import com.example.factureservice.client.ClientClient;
import com.example.factureservice.dto.FactureRequest;
import com.example.factureservice.dto.FactureResponse;
import com.example.factureservice.dto.FactureUpdate;
import com.example.factureservice.mapper.FactureMapper;
import com.example.factureservice.model.Facture;
import com.example.factureservice.model.FactureStatut;
import com.example.factureservice.repository.FactureRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;
    private final FactureMapper factureMapper;
    private final ClientClient clientClient;

    @Override
    @Transactional(readOnly = true)
    public List<FactureResponse> getAllFactures() {
        return StreamSupport.stream(factureRepository.findAll().spliterator(), false)
                .map(factureMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FactureResponse> getFactureById(Long id) {
        return factureRepository.findById(id)
                .map(factureMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FactureResponse> getFactureByNumero(String numero) {
        return factureRepository.findByNumeroFacture(numero)
                .map(factureMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FactureResponse> getFacturesByClientId(Long clientId) {
        return factureRepository.findByClientId(clientId)
                .stream()
                .map(factureMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FactureResponse> getFacturesByStatut(String statut) {
        FactureStatut statutEnum = FactureStatut.valueOf(statut.toUpperCase());
        return factureRepository.findByStatut(statutEnum)
                .stream()
                .map(factureMapper::toResponse)
                .toList();
    }

    @Override
    public FactureResponse createFacture(FactureRequest request) {
        fetchClient(request.getClientId());

        Facture facture = factureMapper.toEntity(request);
        facture.setNumeroFacture(generateNumeroFacture());
        Facture saved = factureRepository.save(facture);
        return factureMapper.toResponse(saved);
    }

    @Override
    public Optional<FactureResponse> updateFacture(Long id, FactureUpdate update) {
        return factureRepository.findById(id)
                .map(facture -> {
                    factureMapper.updateEntity(facture, update);
                    Facture saved = factureRepository.save(facture);
                    return factureMapper.toResponse(saved);
                });
    }

    @Override
    public void deleteFacture(Long id) {
        factureRepository.findById(id).ifPresent(facture -> {
            facture.setStatut(FactureStatut.CANCELLED);
            factureRepository.save(facture);
        });
    }

    @Override
    public void markAsPaid(Long id) {
        factureRepository.findById(id).ifPresent(facture -> {
            facture.setStatut(FactureStatut.PAID);
            factureRepository.save(facture);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FactureResponse> getOverdueFactures() {
        LocalDate today = LocalDate.now();
        return factureRepository.findByStatutAndDateEcheanceBefore(FactureStatut.PENDING, today)
                .stream()
                .map(factureMapper::toResponse)
                .toList();
    }

    private String generateNumeroFacture() {
        return "FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private Map<String, Object> fetchClient(Long clientId) {
        try {
            Map<String, Object> client = clientClient.getClientById(clientId);
            if (client == null || client.isEmpty()) {
                throw new RuntimeException("Client not found with id " + clientId);
            }
            return client;
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Client not found with id " + clientId);
        } catch (FeignException e) {
            throw new RuntimeException("Unable to contact client service");
        }
    }
}
