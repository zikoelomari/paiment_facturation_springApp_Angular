package com.example.notificationservice.dto;

import lombok.Data;

@Data
public class ClientDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String status;
}
