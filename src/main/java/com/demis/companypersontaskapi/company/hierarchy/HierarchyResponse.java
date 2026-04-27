package com.demis.companypersontaskapi.company.hierarchy;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Company hierarchy response")
public record HierarchyResponse(
        @Schema(description = "Company identifier", example = "company-1")
        String companyId,
        @Schema(description = "Company name", example = "Company 1")
        String companyName,
        @Schema(description = "Persons related to the company after applying filters")
        List<PersonHierarchyResponse> persons
) {
}
