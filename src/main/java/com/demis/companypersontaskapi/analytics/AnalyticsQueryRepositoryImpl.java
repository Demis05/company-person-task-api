package com.demis.companypersontaskapi.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnalyticsQueryRepositoryImpl implements AnalyticsQueryRepository {

    private final Neo4jClient neo4jClient;

    private static final String TOP_TASKS_QUERY = """
            MATCH (p:Person)-[pt:WORKS_ON]->(t:Task)
            WHERE ($participationType IS NULL OR pt.participationType = $participationType)
            RETURN t.id AS taskId,
                   t.title AS title,
                   count(DISTINCT p) AS personCount
            ORDER BY personCount DESC, title ASC
            LIMIT $limit
            """;

    private static final String TOP_PERSONS_QUERY = """
            MATCH (p:Person)-[pt:WORKS_ON]->(t:Task)
            WHERE ($participationType IS NULL OR pt.participationType = $participationType)
            RETURN p.id AS personId,
                   p.firstName AS firstName,
                   p.lastName AS lastName,
                   count(DISTINCT t) AS taskCount
            ORDER BY taskCount DESC, firstName ASC, lastName ASC
            LIMIT $limit
            """;

    @Override
    public List<TopTaskSummary> fetchTopTasks(int limit, String participationType) {
        return List.copyOf(neo4jClient.query(TOP_TASKS_QUERY)
                .bindAll(parameters(limit, participationType))
                .fetchAs(TopTaskSummary.class)
                .mappedBy((typeSystem, record) -> new TopTaskSummary(
                        record.get("taskId").asString(),
                        record.get("title").asString(),
                        record.get("personCount").asLong()
                ))
                .all());
    }

    @Override
    public List<TopPersonSummary> fetchTopPersons(int limit, String participationType) {
        return List.copyOf(neo4jClient.query(TOP_PERSONS_QUERY)
                .bindAll(parameters(limit, participationType))
                .fetchAs(TopPersonSummary.class)
                .mappedBy((typeSystem, record) -> new TopPersonSummary(
                        record.get("personId").asString(),
                        record.get("firstName").asString(),
                        record.get("lastName").asString(),
                        record.get("taskCount").asLong()
                ))
                .all());
    }

    private Map<String, Object> parameters(int limit, String participationType) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("limit", limit);
        parameters.put("participationType", participationType);
        return parameters;
    }
}
