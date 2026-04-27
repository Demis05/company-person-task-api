package com.demis.companypersontaskapi.company.hierarchy;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Tag(name = "Company Hierarchy", description = "Hierarchy traversal from company to persons and tasks")
public class HierarchyController {

    private final HierarchyService hierarchyService;

    @GetMapping("/{companyId}/hierarchy")
    @Operation(
            summary = "Get company hierarchy",
            description = "Returns the tree Company -> Persons -> Tasks with optional filtering by task ids, person position, and participation type."
    )
    public HierarchyResponse getHierarchy(
            @Parameter(description = "Company identifier", example = "company-1")
            @PathVariable String companyId,
            @Parameter(description = "Optional task identifiers to include", example = "task-1")
            @RequestParam(required = false) List<String> taskIds,
            @Parameter(description = "Optional person position filter on the Company -> Person relationship", example = "CTO")
            @RequestParam(required = false) String personPosition,
            @Parameter(description = "Optional participation type filter on the Person -> Task relationship", example = "Owner")
            @RequestParam(required = false) String participationType
    ) {
        return hierarchyService.getHierarchy(companyId, taskIds, personPosition, participationType);
    }
}
