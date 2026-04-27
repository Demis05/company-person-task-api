package com.demis.companypersontaskapi.person;

import java.util.HashSet;
import java.util.Set;

import com.demis.companypersontaskapi.relationship.PersonTaskRelationship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @Relationship(type = "WORKS_ON")
    private Set<PersonTaskRelationship> tasks = new HashSet<>();
}
