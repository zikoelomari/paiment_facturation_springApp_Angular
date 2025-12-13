package com.example.paiementservice.service;

import com.example.paiementservice.client.FactureClient;
import com.example.paiementservice.dto.PaiementRequest;
import com.example.paiementservice.dto.PaiementResponse;
import com.example.paiementservice.mapper.PaiementMapper;
import com.example.paiementservice.messaging.RabbitMQProducer;
import com.example.paiementservice.model.Paiement;
import com.example.paiementservice.model.PaiementStatut;
import com.example.paiementservice.repository.PaiementRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;
    private final FactureClient factureClient;
    private final RabbitMQProducer rabbitMQProducer;

    @Override
    @Transactional(readOnly = true)
    public List<PaiementResponse> getAllPaiements() {
        return StreamSupport.stream(paiementRepository.findAll().spliterator(), false)
                .map(paiementMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaiementResponse> getPaiementById(Long id) {
        return paiementRepository.findById(id)
                .map(paiementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementResponse> getPaiementsByFactureId(Long factureId) {
        return paiementRepository.findByFactureId(factureId)
                .stream()
                .map(paiementMapper::toResponse)
                .toList();
    }

    @Override
    public PaiementResponse processPaiement(PaiementRequest request) {
        Map<String, Object> facture = fetchFacture(request.getFactureId());
        BigDecimal factureMontant = extractMontant(facture);

        if (factureMontant.compareTo(request.getMontant()) != 0) {
            throw new RuntimeException("Paiement montant mismatch with facture total");
        }

        Object statutObj = facture.get("statut");
        if (statutObj != null && "PAID".equalsIgnoreCase(statutObj.toString())) {
            throw new RuntimeException("Facture already paid");
        }

        Paiement paiement = paiementMapper.toEntity(request);
        paiement.setStatut(PaiementStatut.COMPLETED);
        paiement.setDatePaiement(LocalDateTime.now());

        Paiement saved = paiementRepository.save(paiement);

        boolean factureUpdated = true;
        try {
            factureClient.markAsPaid(request.getFactureId());
        } catch (Exception e) {
            factureUpdated = false;
            log.error("Error updating facture status", e);
        }

        if (factureUpdated) {
            rabbitMQProducer.sendPaymentCompletedEvent(
                    saved.getId(),
                    saved.getFactureId(),
                    saved.getMontant()
            );
        }

        return paiementMapper.toResponse(saved);
    }

    private Map<String, Object> fetchFacture(Long factureId) {
        try {
            Map<String, Object> facture = factureClient.getFactureById(factureId);
            if (facture == null || facture.isEmpty()) {
                throw new RuntimeException("Facture not found with id " + factureId);
            }
            return facture;
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Facture not found with id " + factureId);
        } catch (FeignException e) {
            throw new RuntimeException("Unable to contact facture service");
        }
    }

    private BigDecimal extractMontant(Map<String, Object> facture) {
        Object montantObj = facture.get("montant");
        if (montantObj instanceof BigDecimal montant) {
            return montant;
        }
        if (montantObj != null) {
            try {
                return new BigDecimal(montantObj.toString());
            } catch (NumberFormatException ignored) {
                // fall through to error below
            }
        }
        throw new RuntimeException("Invalid facture data: montant missing");
    }
}
