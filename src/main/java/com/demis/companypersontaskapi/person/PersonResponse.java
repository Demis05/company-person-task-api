package com.demis.companypersontaskapi.person;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Person response model")
public record PersonResponse(
        @Schema(description = "Person identifier", example = "person-1")
        String id,
        @Schema(description = "Person first name", example = "John")
        String firstName,
        @Schema(description = "Person last name", example = "Smith")
        String lastName
) {
}
