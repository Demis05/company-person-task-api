package com.demis.companypersontaskapi;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic paginated response")
public record PageResponse<T>(
        @Schema(description = "Returned items for the current page")
        List<T> content,
        @Schema(description = "Current page index", example = "0")
        int page,
        @Schema(description = "Requested page size", example = "20")
        int size,
        @Schema(description = "Total number of available items", example = "100")
        long totalElements,
        @Schema(description = "Total number of pages", example = "5")
        int totalPages,
        @Schema(description = "Whether this is the first page", example = "true")
        boolean first,
        @Schema(description = "Whether this is the last page", example = "false")
        boolean last
) {
}
