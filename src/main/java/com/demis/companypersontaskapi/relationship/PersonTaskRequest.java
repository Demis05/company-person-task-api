package com.demis.companypersontaskapi.relationship;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for creating or updating a Person -> Task relationship")
public record PersonTaskRequest(
        @Schema(description = "Participation type of the person in the task", example = "Owner")
        @NotBlank(message = "Participation type is required")
        String participationType
) {
}
