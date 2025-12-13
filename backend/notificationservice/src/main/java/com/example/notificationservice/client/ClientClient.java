package com.example.notificationservice.client;

import com.example.notificationservice.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientservice", path = "/api/clients")
public interface ClientClient {

    @GetMapping("/{id}")
    ClientDto getClientById(@PathVariable("id") Long id);
}
