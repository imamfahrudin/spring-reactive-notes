package com.imamfahrudin.notes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration class for API documentation.
 * This configuration defines the OpenAPI/Swagger documentation settings
 * for the Spring Reactive Notes API.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Spring Reactive Notes API",
        version = "1.0",
        description = "API for managing notes in a reactive Spring Boot application"
    )
)
public class OpenApiConfig {
}
