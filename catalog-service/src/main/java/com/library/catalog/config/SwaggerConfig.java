package com.library.catalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Catalog Service API")
                        .description("REST API for Catalog Service - manages books, book copies, members, and staff")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Library Management Team")
                                .email("support@library.com")));
    }
}
