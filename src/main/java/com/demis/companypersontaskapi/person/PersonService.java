package com.demis.companypersontaskapi.person;

import java.util.HashSet;
import java.util.UUID;

import com.demis.companypersontaskapi.PageResponse;
import com.demis.companypersontaskapi.PageResponseMapper;
import com.demis.companypersontaskapi.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PageResponseMapper pageResponseMapper;

    public PersonResponse create(PersonRequest request) {
        Person person = new Person(UUID.randomUUID().toString(), request.firstName(), request.lastName(), new HashSet<>());
        Person saved = personRepository.save(person);
        return personMapper.toResponse(saved);
    }

    public PersonResponse getById(String id) {
        return personMapper.toResponse(getEntityById(id));
    }

    public PageResponse<PersonResponse> getAll(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(parseDirection(sortDir), sortBy));
        Page<PersonResponse> personPage = personRepository.findAll(pageable).map(personMapper::toResponse);
        return pageResponseMapper.toResponse(personPage);
    }

    public PersonResponse update(String id, PersonRequest request) {
        Person person = getEntityById(id);
        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        return personMapper.toResponse(save(person));
    }

    public void delete(String id) {
        personRepository.deleteById(id);
    }

    public Person getEntityById(String id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id %s not found".formatted(id)));
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    private static Sort.Direction parseDirection(String sortDir) {
        return "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
