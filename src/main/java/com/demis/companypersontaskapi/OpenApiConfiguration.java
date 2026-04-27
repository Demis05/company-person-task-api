package com.demis.companypersontaskapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI companyPersonTaskOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Company Person Task API")
                .version("1.0")
                .description("REST API for managing companies, persons, tasks, relationships, hierarchy, and analytics.")
                .contact(new Contact().name("Demis")));
    }
}
