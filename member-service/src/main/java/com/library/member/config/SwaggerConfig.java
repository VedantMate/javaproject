package com.library.member.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI memberServiceAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Member Service API")
                .description("Member & Staff Management Service for Library Management System")
                .version("1.0.0"));
    }
}
