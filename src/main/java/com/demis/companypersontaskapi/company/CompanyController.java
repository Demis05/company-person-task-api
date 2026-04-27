package com.demis.companypersontaskapi.company;

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
@RequestMapping("/companies")
@Tag(name = "Companies", description = "CRUD operations for company entities")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create company", description = "Creates a new company node.")
    public CompanyResponse create(@Valid @RequestBody CompanyRequest request) {
        return companyService.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by id", description = "Returns a single company by its identifier.")
    public CompanyResponse getById(@PathVariable String id) {
        return companyService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get companies", description = "Returns a paginated and sortable list of companies.")
    public PageResponse<CompanyResponse> getAll(
            @Parameter(description = "Zero-based page index", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Field used for sorting", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return companyService.getAll(page, size, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update company", description = "Updates an existing company by id.")
    public CompanyResponse update(@PathVariable String id, @Valid @RequestBody CompanyRequest request) {
        return companyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete company", description = "Deletes a company by id. If the company does not exist, the operation still completes successfully.")
    public void delete(@PathVariable String id) {
        companyService.delete(id);
    }
}
