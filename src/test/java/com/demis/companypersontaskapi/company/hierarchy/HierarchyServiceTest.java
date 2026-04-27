package com.demis.companypersontaskapi.company.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.HashSet;

import com.demis.companypersontaskapi.ResourceNotFoundException;
import com.demis.companypersontaskapi.company.Company;
import com.demis.companypersontaskapi.company.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HierarchyServiceTest {

    @Mock
    private CompanyService companyService;

    @Mock
    private HierarchyQueryRepository hierarchyQueryRepository;

    @InjectMocks
    private HierarchyService hierarchyService;

    @Test
    void getHierarchyShouldBuildTreeFromRows() {
        // given
        when(companyService.getEntityById("c1")).thenReturn(new Company("c1", "Acme", new HashSet<>()));
        when(hierarchyQueryRepository.fetchHierarchy("c1", List.of("t1", "t2"), "Manager", "Owner"))
                .thenReturn(List.of(
                        new HierarchyModel("p1", "John", "Smith", "Manager", "t1", "Launch MVP", "Desc 1", "Owner"),
                        new HierarchyModel("p1", "John", "Smith", "Manager", "t2", "QA", "Desc 2", "Owner")
                ));

        // when
        HierarchyResponse response = hierarchyService.getHierarchy(
                "c1",
                List.of("t1", "t2"),
                "Manager",
                "Owner"
        );

        // then
        assertThat(response.companyId()).isEqualTo("c1");
        assertThat(response.companyName()).isEqualTo("Acme");
        assertThat(response.persons()).hasSize(1);
        assertThat(response.persons().get(0).tasks()).hasSize(2);
    }

    @Test
    void getHierarchyShouldThrowWhenCompanyMissing() {
        // given
        when(companyService.getEntityById("missing"))
                .thenThrow(new ResourceNotFoundException("Company with id missing not found"));

        // when // then
        assertThatThrownBy(() -> hierarchyService.getHierarchy("missing", null, null, null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("missing");
    }
}
