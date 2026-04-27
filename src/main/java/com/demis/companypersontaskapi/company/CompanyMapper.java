package com.demis.companypersontaskapi.company;

import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse(Company company) {
        return new CompanyResponse(company.getId(), company.getName());
    }
}
