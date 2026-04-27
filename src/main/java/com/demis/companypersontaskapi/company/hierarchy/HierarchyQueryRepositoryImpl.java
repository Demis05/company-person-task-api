package com.demis.companypersontaskapi.company.hierarchy;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HierarchyQueryRepositoryImpl implements HierarchyQueryRepository {

    private final Neo4jClient neo4jClient;
    private static final String HIERARCHY_QUERY = """
            MATCH (c:Company {id: $companyId})-[cp:HAS_PERSON]->(p:Person)-[pt:WORKS_ON]->(t:Task)
            WHERE ($personPosition IS NULL OR cp.position = $personPosition)
              AND ($participationType IS NULL OR pt.participationType = $participationType)
              AND ($taskIds IS NULL OR t.id IN $taskIds)
            RETURN p.id AS personId,
                   p.firstName AS firstName,
                   p.lastName AS lastName,
                   cp.position AS personPosition,
                   t.id AS taskId,
                   t.title AS taskTitle,
                   t.description AS taskDescription,
                   pt.participationType AS participationType
            ORDER BY p.firstName, p.lastName, t.title
            """;

    @Override
    public List<HierarchyModel> fetchHierarchy(String companyId, List<String> taskIds,
                                               String personPosition, String participationType) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("companyId", companyId);
        parameters.put("taskIds", taskIds);
        parameters.put("personPosition", personPosition);
        parameters.put("participationType", participationType);

        return List.copyOf(neo4jClient.query(HIERARCHY_QUERY)
                .bindAll(parameters)
                .fetchAs(HierarchyModel.class)
                .mappedBy((typeSystem, record) -> new HierarchyModel(
                        record.get("personId").asString(),
                        record.get("firstName").asString(),
                        record.get("lastName").asString(),
                        record.get("personPosition").asString(),
                        record.get("taskId").asString(),
                        record.get("taskTitle").asString(),
                        record.get("taskDescription").isNull() ? null : record.get("taskDescription").asString(),
                        record.get("participationType").asString()
                ))
                .all());
    }
}
