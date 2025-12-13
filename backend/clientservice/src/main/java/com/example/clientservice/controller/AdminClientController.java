package com.example.clientservice.controller;

import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.dto.RoleRequest;
import com.example.clientservice.model.Role;
import com.example.clientservice.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/clients")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminClientController {

    private final ClientService clientService;

    @PatchMapping("/{id}/roles")
    public ResponseEntity<ClientResponse> assignRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        Role role = Role.valueOf(request.getRole().toUpperCase());
        ClientResponse updated = clientService.assignRole(id, role);
        return ResponseEntity.ok(updated);
    }
}
