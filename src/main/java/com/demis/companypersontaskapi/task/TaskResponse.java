package com.demis.companypersontaskapi.task;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task response model")
public record TaskResponse(
        @Schema(description = "Task identifier", example = "task-1")
        String id,
        @Schema(description = "Task title", example = "Launch MVP")
        String title,
        @Schema(description = "Task description", example = "Initial release")
        String description
) {
}
