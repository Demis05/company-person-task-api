package com.demis.companypersontaskapi.company;

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
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Spy
    private CompanyMapper companyMapper;

    @Spy
    private PageResponseMapper pageResponseMapper;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void createShouldPersistCompany() {
        // given
        when(companyRepository.save(any(Company.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        CompanyResponse response = companyService.create(new CompanyRequest("Acme"));

        // then
        assertThat(response.name()).isEqualTo("Acme");
        assertThat(response.id()).isNotBlank();
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void getByIdShouldReturnCompanyWhenFound() {
        // given
        when(companyRepository.findById("c1")).thenReturn(Optional.of(new Company("c1", "Acme", new HashSet<>())));

        // when
        CompanyResponse response = companyService.getById("c1");

        // then
        assertThat(response.id()).isEqualTo("c1");
        assertThat(response.name()).isEqualTo("Acme");
    }

    @Test
    void getByIdShouldThrowWhenCompanyMissing() {
        // given
        when(companyRepository.findById("missing")).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> companyService.getById("missing"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("missing");
    }
}
