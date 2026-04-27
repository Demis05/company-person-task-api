package com.demis.companypersontaskapi.company;

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
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final PageResponseMapper pageResponseMapper;

    public CompanyResponse create(CompanyRequest request) {
        Company company = new Company(UUID.randomUUID().toString(), request.name(), new HashSet<>());
        Company saved = companyRepository.save(company);
        return companyMapper.toResponse(saved);
    }

    public CompanyResponse getById(String id) {
        return companyMapper.toResponse(getEntityById(id));
    }

    public PageResponse<CompanyResponse> getAll(int page, int size, String sortBy, String sortDir) {
        Sort.Direction sort = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sort, sortBy);
        Page<CompanyResponse> companyPage = companyRepository.findAll(pageable).map(companyMapper::toResponse);
        return pageResponseMapper.toResponse(companyPage);
    }

    public CompanyResponse update(String id, CompanyRequest request) {
        Company company = getEntityById(id);
        company.setName(request.name());
        Company updated = save(company);
        return companyMapper.toResponse(updated);
    }

    public void delete(String id) {
        companyRepository.deleteById(id);
    }

    public Company getEntityById(String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company with id %s not found".formatted(id)));
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

}
