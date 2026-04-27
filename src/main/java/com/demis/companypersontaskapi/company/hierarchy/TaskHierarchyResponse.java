package com.demis.companypersontaskapi.company.hierarchy;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task node inside the hierarchy response")
public record TaskHierarchyResponse(
        @Schema(description = "Task identifier", example = "task-1")
        String taskId,
        @Schema(description = "Task title", example = "Launch MVP")
        String title,
        @Schema(description = "Task description", example = "Initial release")
        String description,
        @Schema(description = "Participation type of the person in the task", example = "Owner")
        String participationType
) {
}
