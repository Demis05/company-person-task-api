package com.demis.companypersontaskapi.relationship;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Relationships", description = "Operations for managing Company-Person and Person-Task relationships")
public class RelationshipController {

    private final RelationshipService relationshipService;

    @PostMapping("/companies/{companyId}/persons/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create company-person relationship", description = "Creates a relationship between a company and a person with the personPosition attribute.")
    public RelationshipAttributeResponse createCompanyPersonRelationship(
            @PathVariable String companyId,
            @PathVariable String personId,
            @Valid @RequestBody CompanyPersonRequest request
    ) {
        return relationshipService.createCompanyPersonRelationship(companyId, personId, request);
    }

    @PutMapping("/companies/{companyId}/persons/{personId}")
    @Operation(summary = "Update company-person relationship", description = "Updates the personPosition attribute for an existing relationship between a company and a person.")
    public RelationshipAttributeResponse updateCompanyPersonRelationship(
            @PathVariable String companyId,
            @PathVariable String personId,
            @Valid @RequestBody CompanyPersonRequest request
    ) {
        return relationshipService.updateCompanyPersonRelationship(companyId, personId, request);
    }

    @DeleteMapping("/companies/{companyId}/persons/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete company-person relationship", description = "Deletes the relationship between a company and a person.")
    public void deleteCompanyPersonRelationship(
            @PathVariable String companyId,
            @PathVariable String personId
    ) {
        relationshipService.deleteCompanyPersonRelationship(companyId, personId);
    }

    @PostMapping("/persons/{personId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create person-task relationship", description = "Creates a relationship between a person and a task with the participationType attribute.")
    public RelationshipAttributeResponse createPersonTaskRelationship(
            @PathVariable String personId,
            @PathVariable String taskId,
            @Valid @RequestBody PersonTaskRequest request
    ) {
        return relationshipService.createPersonTaskRelationship(personId, taskId, request);
    }

    @PutMapping("/persons/{personId}/tasks/{taskId}")
    @Operation(summary = "Update person-task relationship", description = "Updates the participationType attribute for an existing relationship between a person and a task.")
    public RelationshipAttributeResponse updatePersonTaskRelationship(
            @PathVariable String personId,
            @PathVariable String taskId,
            @Valid @RequestBody PersonTaskRequest request
    ) {
        return relationshipService.updatePersonTaskRelationship(personId, taskId, request);
    }

    @DeleteMapping("/persons/{personId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete person-task relationship", description = "Deletes the relationship between a person and a task.")
    public void deletePersonTaskRelationship(
            @PathVariable String personId,
            @PathVariable String taskId
    ) {
        relationshipService.deletePersonTaskRelationship(personId, taskId);
    }
}
