package com.example.factureservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "clientservice", path = "/api/clients")
public interface ClientClient {

    @GetMapping("/{id}")
    Map<String, Object> getClientById(@PathVariable("id") Long id);
}

