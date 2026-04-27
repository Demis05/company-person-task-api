package com.demis.companypersontaskapi.company;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for creating or updating a company")
public record CompanyRequest(
        @Schema(description = "Company name", example = "Acme")
        @NotBlank(message = "Name is required")
        String name
) {
}
