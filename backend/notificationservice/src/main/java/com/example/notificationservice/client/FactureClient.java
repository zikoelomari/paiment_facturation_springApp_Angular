package com.example.notificationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "factureservice", path = "/api/factures")
public interface FactureClient {

    @GetMapping("/{id}")
    Map<String, Object> getFactureById(@PathVariable("id") Long id);
}

