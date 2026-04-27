package com.demis.companypersontaskapi.relationship;

import com.demis.companypersontaskapi.task.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonTaskRelationship {

    @RelationshipId
    private Long id;

    private String participationType;

    @TargetNode
    private Task task;

    public PersonTaskRelationship(String participationType, Task task) {
        this.participationType = participationType;
        this.task = task;
    }
}
