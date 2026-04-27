package com.demis.companypersontaskapi.company;

import java.util.HashSet;
import java.util.Set;

import com.demis.companypersontaskapi.relationship.CompanyPersonRelationship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    private String id;

    private String name;

    @Relationship(type = "HAS_PERSON")
    private Set<CompanyPersonRelationship> persons = new HashSet<>();
}
