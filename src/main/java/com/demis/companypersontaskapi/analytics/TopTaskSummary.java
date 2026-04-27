package com.demis.companypersontaskapi.analytics;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary of a top task by number of related persons")
public record TopTaskSummary(
        @Schema(description = "Task identifier", example = "task-1")
        String taskId,
        @Schema(description = "Task title", example = "Task 1")
        String title,
        @Schema(description = "Number of unique persons related to the task", example = "8")
        long personCount
) {
}
