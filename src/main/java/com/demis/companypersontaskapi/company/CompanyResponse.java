package com.demis.companypersontaskapi.company;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Company response model")
public record CompanyResponse(
        @Schema(description = "Company identifier", example = "company-1")
        String id,
        @Schema(description = "Company name", example = "Acme")
        String name
) {
}
