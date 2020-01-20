package com.iucalyptus.gatewayservice.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfiguration {

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r.method(HttpMethod.GET)
            .and()
            .path("/auth")
            .filters(f -> f.rewritePath("/auth", "/token"))
            .uri("http://localhost:8081"))
        .route(r -> r.method(HttpMethod.GET)
            .and()
            .path("/users")
            .and()
            .predicate(f -> f.getRequest().getHeaders().containsKey(AUTHORIZATION))
            .filters(f -> f.rewritePath("/users", "/getUsers"))
            .uri("http://localhost:8082"))
        .build();
  }
}
