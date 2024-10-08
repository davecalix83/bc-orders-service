package com.cuscatlan.orders.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger API documentation.
 * <p>
 * This class configures Swagger for the Cuscatlan Order Service API,
 * providing metadata for the generated API documentation.
 * </p>
 */
@Configuration
public class SwaggerConfig {

    /**
     * Provides an OpenAPI configuration with metadata for the API.
     *
     * @return an OpenAPI instance containing API information
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cuscatlan Order Service API")
                        .version("1.0")
                        .description("Microservice for managing orders"));
    }
}
