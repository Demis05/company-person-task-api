package com.demis.companypersontaskapi.person;

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
@RequestMapping("/persons")
@Tag(name = "Persons", description = "CRUD operations for person entities")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create person", description = "Creates a new person node.")
    public PersonResponse create(@Valid @RequestBody PersonRequest request) {
        return personService.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by id", description = "Returns a single person by its identifier.")
    public PersonResponse getById(@PathVariable String id) {
        return personService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get persons", description = "Returns a paginated and sortable list of persons.")
    public PageResponse<PersonResponse> getAll(
            @Parameter(description = "Zero-based page index", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Field used for sorting", example = "firstName")
            @RequestParam(defaultValue = "firstName") String sortBy,
            @Parameter(description = "Sort direction", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return personService.getAll(page, size, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update person", description = "Updates an existing person by id.")
    public PersonResponse update(@PathVariable String id, @Valid @RequestBody PersonRequest request) {
        return personService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete person", description = "Deletes a person by id. If the person does not exist, the operation still completes successfully.")
    public void delete(@PathVariable String id) {
        personService.delete(id);
    }
}
