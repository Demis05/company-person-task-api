package com.demis.companypersontaskapi.task;

import com.demis.companypersontaskapi.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "CRUD operations for task entities")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create task", description = "Creates a new task node.")
    public TaskResponse create(@Valid @RequestBody TaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id", description = "Returns a single task by its identifier.")
    public TaskResponse getById(@PathVariable String id) {
        return taskService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get tasks", description = "Returns a paginated and sortable list of tasks.")
    public PageResponse<TaskResponse> getAll(
            @Parameter(description = "Zero-based page index", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Field used for sorting", example = "title")
            @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sort direction", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return taskService.getAll(page, size, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task", description = "Updates an existing task by id.")
    public TaskResponse update(@PathVariable String id, @Valid @RequestBody TaskRequest request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete task", description = "Deletes a task by id. If the task does not exist, the operation still completes successfully.")
    public void delete(@PathVariable String id) {
        taskService.delete(id);
    }
}
