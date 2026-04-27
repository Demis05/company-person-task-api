package com.demis.companypersontaskapi.task;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TaskRepository extends Neo4jRepository<Task, String> {
}
