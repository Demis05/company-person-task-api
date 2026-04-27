package com.demis.companypersontaskapi.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import com.demis.companypersontaskapi.ResourceNotFoundException;
import com.demis.companypersontaskapi.PageResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Spy
    private PersonMapper personMapper;

    @Spy
    private PageResponseMapper pageResponseMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void createShouldPersistPerson() {
        // given
        when(personRepository.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        PersonResponse response = personService.create(new PersonRequest("John", "Smith"));

        // then
        assertThat(response.firstName()).isEqualTo("John");
        assertThat(response.lastName()).isEqualTo("Smith");
        assertThat(response.id()).isNotBlank();
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void getByIdShouldReturnPersonWhenFound() {
        // given
        when(personRepository.findById("p1")).thenReturn(Optional.of(new Person("p1", "John", "Smith", new HashSet<>())));

        // when
        PersonResponse response = personService.getById("p1");

        // then
        assertThat(response.id()).isEqualTo("p1");
        assertThat(response.firstName()).isEqualTo("John");
        assertThat(response.lastName()).isEqualTo("Smith");
    }

    @Test
    void getByIdShouldThrowWhenPersonMissing() {
        // given
        when(personRepository.findById("missing")).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> personService.getById("missing"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("missing");
    }
}
