package com.demis.companypersontaskapi.person;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for creating or updating a person")
public record PersonRequest(
        @Schema(description = "Person first name", example = "John")
        @NotBlank(message = "First name is required")
        String firstName,
        @Schema(description = "Person last name", example = "Smith")
        @NotBlank(message = "Last name is required")
        String lastName
) {
}
