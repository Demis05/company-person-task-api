package com.demis.companypersontaskapi.relationship;

import java.util.Objects;

import com.demis.companypersontaskapi.RelationshipConflictException;
import com.demis.companypersontaskapi.ResourceNotFoundException;
import com.demis.companypersontaskapi.company.Company;
import com.demis.companypersontaskapi.company.CompanyService;
import com.demis.companypersontaskapi.person.Person;
import com.demis.companypersontaskapi.person.PersonService;
import com.demis.companypersontaskapi.task.Task;
import com.demis.companypersontaskapi.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RelationshipService {

    private final CompanyService companyService;
    private final PersonService personService;
    private final TaskService taskService;

    public RelationshipAttributeResponse createCompanyPersonRelationship(String companyId, String personId, CompanyPersonRequest request) {
        Company company = companyService.getEntityById(companyId);
        Person person = personService.getEntityById(personId);

        if (findCompanyPersonRelationship(company, personId) != null) {
            throw new RelationshipConflictException(
                    "Relationship between company %s and person %s already exists".formatted(companyId, personId)
            );
        }

        company.getPersons().add(new CompanyPersonRelationship(request.personPosition(), person));
        companyService.save(company);
        return new RelationshipAttributeResponse(companyId, personId, "personPosition", request.personPosition());
    }

    public RelationshipAttributeResponse updateCompanyPersonRelationship(String companyId, String personId, CompanyPersonRequest request) {
        Company company = companyService.getEntityById(companyId);
        CompanyPersonRelationship relationship = findCompanyPersonRelationship(company, personId);

        if (relationship == null) {
            throw new ResourceNotFoundException(
                    "Relationship between company %s and person %s not found".formatted(companyId, personId)
            );
        }

        relationship.setPosition(request.personPosition());
        companyService.save(company);
        return new RelationshipAttributeResponse(companyId, personId, "personPosition", request.personPosition());
    }

    public void deleteCompanyPersonRelationship(String companyId, String personId) {
        Company company = companyService.getEntityById(companyId);
        companyService.save(company);
    }

    public RelationshipAttributeResponse createPersonTaskRelationship(String personId, String taskId, PersonTaskRequest request) {
        Person person = personService.getEntityById(personId);
        Task task = taskService.getEntityById(taskId);

        if (findPersonTaskRelationship(person, taskId) != null) {
            throw new RelationshipConflictException(
                    "Relationship between person %s and task %s already exists".formatted(personId, taskId)
            );
        }

        person.getTasks().add(new PersonTaskRelationship(request.participationType(), task));
        personService.save(person);
        return new RelationshipAttributeResponse(personId, taskId, "participationType", request.participationType());
    }

    public RelationshipAttributeResponse updatePersonTaskRelationship(String personId, String taskId, PersonTaskRequest request) {
        Person person = personService.getEntityById(personId);
        PersonTaskRelationship relationship = findPersonTaskRelationship(person, taskId);

        if (relationship == null) {
            throw new ResourceNotFoundException(
                    "Relationship between person %s and task %s not found".formatted(personId, taskId)
            );
        }

        relationship.setParticipationType(request.participationType());
        personService.save(person);
        return new RelationshipAttributeResponse(personId, taskId, "participationType", request.participationType());
    }

    public void deletePersonTaskRelationship(String personId, String taskId) {
        Person person = personService.getEntityById(personId);
        person.getTasks().removeIf(relationship -> hasTaskId(relationship, taskId));
        personService.save(person);
    }

    private CompanyPersonRelationship findCompanyPersonRelationship(Company company, String personId) {
        return company.getPersons().stream()
                .filter(relationship -> hasPersonId(relationship, personId))
                .findFirst()
                .orElse(null);
    }

    private PersonTaskRelationship findPersonTaskRelationship(Person person, String taskId) {
        return person.getTasks().stream()
                .filter(relationship -> hasTaskId(relationship, taskId))
                .findFirst()
                .orElse(null);
    }

    private boolean hasPersonId(CompanyPersonRelationship relationship, String personId) {
        return relationship.getPerson() != null && Objects.equals(relationship.getPerson().getId(), personId);
    }

    private boolean hasTaskId(PersonTaskRelationship relationship, String taskId) {
        return relationship.getTask() != null && Objects.equals(relationship.getTask().getId(), taskId);
    }
}
