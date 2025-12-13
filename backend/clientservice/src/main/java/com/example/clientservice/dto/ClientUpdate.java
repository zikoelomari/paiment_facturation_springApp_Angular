package com.example.clientservice.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdate {

    @Email(message = "Email must be valid")
    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String address;

    private String status;
}

