package com.example.clientservice.service;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.dto.ClientUpdate;
import com.example.clientservice.model.Role;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientResponse> getAllClients();
    Optional<ClientResponse> getClientById(Long id);
    Optional<ClientResponse> getClientByEmail(String email);
    List<ClientResponse> searchClientsByName(String name);
    List<ClientResponse> searchClientsByEmail(String email);
    ClientResponse createClient(ClientRequest request);
    Optional<ClientResponse> updateClient(Long id, ClientUpdate update);
    void deleteClient(Long id);
    void updateLastLogin(Long id);
    ClientResponse assignRole(Long id, Role role);
}
