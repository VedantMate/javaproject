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
                        .title("Library Management System API")
                        .description("REST API for Library Management System with Admin, Librarian, and Manager functionalities")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Library Management Team")
                                .email("support@library.com")));
    }
}
