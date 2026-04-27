package com.demis.companypersontaskapi.relationship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import com.demis.companypersontaskapi.RelationshipConflictException;
import com.demis.companypersontaskapi.ResourceNotFoundException;
import com.demis.companypersontaskapi.company.Company;
import com.demis.companypersontaskapi.company.CompanyService;
import com.demis.companypersontaskapi.person.Person;
import com.demis.companypersontaskapi.person.PersonService;
import com.demis.companypersontaskapi.task.Task;
import com.demis.companypersontaskapi.task.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RelationshipServiceTest {

    @Mock
    private CompanyService companyService;

    @Mock
    private PersonService personService;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private RelationshipService relationshipService;

    @Test
    void createCompanyPersonRelationshipShouldAddRelationship() {
        // given
        Company company = new Company("c1", "Acme", new HashSet<>());
        Person person = new Person("p1", "John", "Smith", new HashSet<>());

        when(companyService.getEntityById("c1")).thenReturn(company);
        when(personService.getEntityById("p1")).thenReturn(person);
        when(companyService.save(any(Company.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        RelationshipAttributeResponse response = relationshipService.createCompanyPersonRelationship(
                "c1",
                "p1",
                new CompanyPersonRequest("Manager")
        );

        // then
        assertThat(response.sourceId()).isEqualTo("c1");
        assertThat(response.targetId()).isEqualTo("p1");
        assertThat(response.attributeValue()).isEqualTo("Manager");
        assertThat(response.attributeName()).isEqualTo("personPosition");
        assertThat(company.getPersons()).hasSize(1);
        verify(companyService).save(company);
    }

    @Test
    void createCompanyPersonRelationshipShouldRejectDuplicate() {
        // given
        Company company = new Company("c1", "Acme", new HashSet<>());
        Person person = new Person("p1", "John", "Smith", new HashSet<>());
        company.getPersons().add(new CompanyPersonRelationship(null, "Manager", person));

        when(companyService.getEntityById("c1")).thenReturn(company);
        when(personService.getEntityById("p1")).thenReturn(person);

        // when // then
        assertThatThrownBy(() -> relationshipService.createCompanyPersonRelationship(
                "c1",
                "p1",
                new CompanyPersonRequest("Director")
        )).isInstanceOf(RelationshipConflictException.class);
    }

    @Test
    void updatePersonTaskRelationshipShouldChangeParticipationType() {
        // given
        Person person = new Person("p1", "John", "Smith", new HashSet<>());
        Task task = new Task("t1", "Launch MVP", "Initial release");
        person.getTasks().add(new PersonTaskRelationship(null, "Reviewer", task));

        when(personService.getEntityById("p1")).thenReturn(person);
        when(personService.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        RelationshipAttributeResponse response = relationshipService.updatePersonTaskRelationship(
                "p1",
                "t1",
                new PersonTaskRequest("Owner")
        );

        // then
        assertThat(response.attributeValue()).isEqualTo("Owner");
        assertThat(person.getTasks().iterator().next().getParticipationType()).isEqualTo("Owner");
        verify(personService).save(person);
    }

    @Test
    void deleteCompanyPersonRelationshipShouldThrowWhenMissing() {
        // given
        Company company = new Company("c1", "Acme", new HashSet<>());
        when(companyService.getEntityById("c1")).thenReturn(company);

        // when // then
        assertThatThrownBy(() -> relationshipService.deleteCompanyPersonRelationship("c1", "p1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Relationship");
    }
}
