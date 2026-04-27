package com.demis.companypersontaskapi.company.hierarchy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.demis.companypersontaskapi.company.Company;
import com.demis.companypersontaskapi.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HierarchyService {

    private final CompanyService companyService;
    private final HierarchyQueryRepository hierarchyQueryRepository;

    public HierarchyResponse getHierarchy(String companyId, List<String> taskIds,
                                          String personPosition, String participationType) {
        Company company = companyService.getEntityById(companyId);

        List<HierarchyModel> rows = hierarchyQueryRepository.fetchHierarchy(
                companyId,
                taskIds == null || taskIds.isEmpty() ? null : taskIds,
                personPosition,
                participationType
        );

        Map<String, PersonHierarchyResponse> persons = new LinkedHashMap<>();

        for (HierarchyModel row : rows) {
            PersonHierarchyResponse person = persons.computeIfAbsent(
                    row.personId(),
                    personId -> new PersonHierarchyResponse(
                            row.personId(),
                            row.firstName(),
                            row.lastName(),
                            row.personPosition(),
                            new ArrayList<>()
                    )
            );

            person.tasks().add(new TaskHierarchyResponse(
                    row.taskId(),
                    row.taskTitle(),
                    row.taskDescription(),
                    row.participationType()
            ));
        }

        return new HierarchyResponse(
                company.getId(),
                company.getName(),
                new ArrayList<>(persons.values())
        );
    }
}
