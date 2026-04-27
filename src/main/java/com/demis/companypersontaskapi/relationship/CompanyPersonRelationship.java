package com.demis.companypersontaskapi.relationship;

import com.demis.companypersontaskapi.person.Person;
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
public class CompanyPersonRelationship {

    @RelationshipId
    private Long id;

    private String position;

    @TargetNode
    private Person person;

    public CompanyPersonRelationship(String position, Person person) {
        this.position = position;
        this.person = person;
    }
}
