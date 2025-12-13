package com.example.paiementservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI paiementServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Paiement Service API")
                        .description("API for processing payments")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Paiement Service Team")
                                .email("support@example.com")));
    }
}

