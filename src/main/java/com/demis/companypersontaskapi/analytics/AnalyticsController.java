package com.demis.companypersontaskapi.analytics;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
@Tag(name = "Analytics", description = "Analytical endpoints for top tasks and top persons")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/tasks/top-multi-user")
    @Operation(summary = "Get top multi-user tasks", description = "Returns top tasks with the highest number of related persons. Can be filtered by participationType.")
    public List<TopTaskSummary> getTopMultiUserTasks(
            @Parameter(description = "Maximum number of records to return", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Optional participation type filter", example = "Owner")
            @RequestParam(required = false) String participationType
    ) {
        return analyticsService.getTopMultiUserTasks(limit, participationType);
    }

    @GetMapping("/persons/top-busy")
    @Operation(summary = "Get top busy persons", description = "Returns top persons with the highest number of related tasks. Can be filtered by participationType.")
    public List<TopPersonSummary> getTopBusyPersons(
            @Parameter(description = "Maximum number of records to return", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Optional participation type filter", example = "Reviewer")
            @RequestParam(required = false) String participationType
    ) {
        return analyticsService.getTopBusyPersons(limit, participationType);
    }
}
