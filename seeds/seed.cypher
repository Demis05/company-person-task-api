CREATE CONSTRAINT company_id IF NOT EXISTS
FOR (c:Company)
REQUIRE c.id IS UNIQUE;

CREATE CONSTRAINT person_id IF NOT EXISTS
FOR (p:Person)
REQUIRE p.id IS UNIQUE;

CREATE CONSTRAINT task_id IF NOT EXISTS
FOR (t:Task)
REQUIRE t.id IS UNIQUE;

MATCH (n)
DETACH DELETE n;

UNWIND range(1, 100) AS i
CREATE (:Company {
  id: 'company-' + toString(i),
  name: 'Company ' + toString(i)
});

UNWIND range(1, 100) AS i
CREATE (:Person {
  id: 'person-' + toString(i),
  firstName: 'Person' + toString(i),
  lastName: 'User' + toString(i)
});

UNWIND range(1, 100) AS i
CREATE (:Task {
  id: 'task-' + toString(i),
  title: 'Task ' + toString(i),
  description: 'Seeded task #' + toString(i)
});

UNWIND range(1, 100) AS i
MATCH (c:Company {id: 'company-' + toString(i)})
UNWIND range(0, 5) AS offset
MATCH (p:Person {id: 'person-' + toString((((i + (offset * 11)) - 1) % 100) + 1)})
MERGE (c)-[r:HAS_PERSON]->(p)
SET r.position =
  CASE ((i + offset) % 6)
    WHEN 0 THEN 'CEO'
    WHEN 1 THEN 'CTO'
    WHEN 2 THEN 'Manager'
    WHEN 3 THEN 'Analyst'
    WHEN 4 THEN 'Engineer'
    ELSE 'Consultant'
  END;

UNWIND range(1, 100) AS i
MATCH (p:Person {id: 'person-' + toString(i)})
UNWIND range(0, 7) AS offset
MATCH (t:Task {id: 'task-' + toString((((i * 3 + (offset * 13)) - 1) % 100) + 1)})
MERGE (p)-[r:WORKS_ON]->(t)
SET r.participationType =
  CASE ((i + offset) % 5)
    WHEN 0 THEN 'Owner'
    WHEN 1 THEN 'Reviewer'
    WHEN 2 THEN 'Executor'
    WHEN 3 THEN 'Observer'
    ELSE 'Consultant'
  END;

MATCH (c:Company)
WITH count(c) AS companies
MATCH (p:Person)
WITH companies, count(p) AS persons
MATCH (t:Task)
WITH companies, persons, count(t) AS tasks
MATCH (:Company)-[cp:HAS_PERSON]->(:Person)
WITH companies, persons, tasks, count(cp) AS companyPersonRelationships
MATCH (:Person)-[pt:WORKS_ON]->(:Task)
RETURN companies, persons, tasks, companyPersonRelationships, count(pt) AS personTaskRelationships;
