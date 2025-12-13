package com.example.clientservice.service;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.dto.ClientUpdate;
import com.example.clientservice.mapper.ClientMapper;
import com.example.clientservice.model.Client;
import com.example.clientservice.model.ClientStatus;
import com.example.clientservice.model.Role;
import com.example.clientservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .map(clientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientResponse> getClientById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientResponse> getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(clientMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> searchClientsByName(String name) {
        return clientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
                .stream()
                .map(clientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> searchClientsByEmail(String email) {
        return clientRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(clientMapper::toResponse)
                .toList();
    }

    @Override
    public ClientResponse createClient(ClientRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        Client client = clientMapper.toEntity(request);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRoles(new java.util.HashSet<>(Set.of(Role.ROLE_USER)));
        Client saved = clientRepository.save(client);
        return clientMapper.toResponse(saved);
    }

    @Override
    public Optional<ClientResponse> updateClient(Long id, ClientUpdate update) {
        return clientRepository.findById(id)
                .map(client -> {
                    if (update.getEmail() != null && !update.getEmail().equals(client.getEmail())) {
                        if (clientRepository.existsByEmail(update.getEmail())) {
                            throw new RuntimeException("Email already exists: " + update.getEmail());
                        }
                    }
                    clientMapper.updateEntity(client, update);
                    if (client.getRoles() == null || client.getRoles().isEmpty()) {
                        client.setRoles(new java.util.HashSet<>(Set.of(Role.ROLE_USER)));
                    }
                    Client saved = clientRepository.save(client);
                    return clientMapper.toResponse(saved);
                });
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setStatus(ClientStatus.INACTIVE);
            client.setUpdatedAt(LocalDateTime.now());
            clientRepository.save(client);
        });
    }

    @Override
    public void updateLastLogin(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setLastLogin(LocalDateTime.now());
            clientRepository.save(client);
        });
    }

    @Override
    public ClientResponse assignRole(Long id, Role role) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + id));
        if (client.getRoles() == null) {
            client.setRoles(new java.util.HashSet<>(Set.of(role)));
        } else {
            client.getRoles().add(role);
        }
        Client saved = clientRepository.save(client);
        return clientMapper.toResponse(saved);
    }
}
