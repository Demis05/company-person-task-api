package com.demis.companypersontaskapi.analytics;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary of a top person by number of related tasks")
public record TopPersonSummary(
        @Schema(description = "Person identifier", example = "person-1")
        String personId,
        @Schema(description = "Person first name", example = "John")
        String firstName,
        @Schema(description = "Person last name", example = "Smith")
        String lastName,
        @Schema(description = "Number of unique tasks related to the person", example = "8")
        long taskCount
) {
}
