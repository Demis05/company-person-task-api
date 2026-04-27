package com.demis.companypersontaskapi.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for creating or updating a task")
public record TaskRequest(
        @Schema(description = "Task title", example = "Launch MVP")
        @NotBlank(message = "Title is required")
        String title,
        @Schema(description = "Task description", example = "Initial release")
        String description
) {
}
