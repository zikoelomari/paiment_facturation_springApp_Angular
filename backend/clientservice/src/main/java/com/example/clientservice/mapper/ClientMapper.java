package com.example.clientservice.mapper;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.dto.ClientUpdate;
import com.example.clientservice.model.Client;
import com.example.clientservice.model.ClientStatus;
import com.example.clientservice.model.Role;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setEmail(request.getEmail());
        client.setPassword(request.getPassword());
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        return client;
    }

    public ClientResponse toResponse(Client client) {
        Set<String> roles = client.getRoles() == null ? Collections.emptySet() :
                client.getRoles().stream().map(Role::name).collect(Collectors.toSet());

        return ClientResponse.builder()
                .id(client.getId())
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phone(client.getPhone())
                .address(client.getAddress())
                .status(client.getStatus())
                .roles(roles)
                .lastLogin(client.getLastLogin())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    public void updateEntity(Client client, ClientUpdate update) {
        if (update.getEmail() != null) {
            client.setEmail(update.getEmail());
        }
        if (update.getFirstName() != null) {
            client.setFirstName(update.getFirstName());
        }
        if (update.getLastName() != null) {
            client.setLastName(update.getLastName());
        }
        if (update.getPhone() != null) {
            client.setPhone(update.getPhone());
        }
        if (update.getAddress() != null) {
            client.setAddress(update.getAddress());
        }
        if (update.getStatus() != null) {
            client.setStatus(ClientStatus.valueOf(update.getStatus().toUpperCase()));
        }
    }
}
