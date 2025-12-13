package com.example.reactivegateway.configurations;

import com.example.reactivegateway.filters.CustomGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Route vers clientservice
                .route("clientservice_route", r -> r
                        .path("/api/clients/**")
                        .filters(f -> f
                                .addRequestHeader("X-Request-Origin", "Gateway")
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://clientservice")
                )

                // Route vers factureservice
                .route("factureservice_route", r -> r
                        .path("/api/factures/**")
                        .filters(f -> f
                                .addRequestHeader("X-Request-Origin", "Gateway")
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://factureservice")
                )

                // Route vers paiementservice
                .route("paiementservice_route", r -> r
                        .path("/api/paiements/**")
                        .filters(f -> f
                                .addRequestHeader("X-Request-Origin", "Gateway")
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://paiementservice")
                )

                // Route vers notificationservice
                .route("notificationservice_route", r -> r
                        .path("/api/notifications/**")
                        .filters(f -> f
                                .addRequestHeader("X-Request-Origin", "Gateway")
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://notificationservice")
                )

                .build();
    }
}
