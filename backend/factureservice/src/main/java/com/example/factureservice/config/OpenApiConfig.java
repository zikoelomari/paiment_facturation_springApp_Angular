package com.example.factureservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI factureServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Facture Service API")
                        .description("API for managing invoices")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Facture Service Team")
                                .email("support@example.com")));
    }
}

