package com.demis.companypersontaskapi.relationship;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for creating or updating a Company -> Person relationship")
public record CompanyPersonRequest(
        @Schema(description = "Position of the person in the company", example = "Manager")
        @NotBlank(message = "Person position is required")
        String personPosition
) {
}
