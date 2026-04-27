package com.demis.companypersontaskapi.company.hierarchy;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Person node inside the hierarchy response")
public record PersonHierarchyResponse(
        @Schema(description = "Person identifier", example = "person-1")
        String personId,
        @Schema(description = "Person first name", example = "John")
        String firstName,
        @Schema(description = "Person last name", example = "Smith")
        String lastName,
        @Schema(description = "Position of the person in the company", example = "CTO")
        String personPosition,
        @Schema(description = "Tasks assigned to the person after applying filters")
        List<TaskHierarchyResponse> tasks
) {
}
