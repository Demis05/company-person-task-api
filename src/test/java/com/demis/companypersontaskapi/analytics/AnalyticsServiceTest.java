package com.demis.companypersontaskapi.analytics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private AnalyticsQueryRepository analyticsQueryRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void getTopMultiUserTasksShouldUseDefaultLimitForInvalidValue() {
        // given
        when(analyticsQueryRepository.fetchTopTasks(10, "Owner"))
                .thenReturn(List.of(new TopTaskSummary("t1", "Launch App", 3)));

        // when
        List<TopTaskSummary> response = analyticsService.getTopMultiUserTasks(0, "Owner");

        // then
        assertThat(response).hasSize(1);
        verify(analyticsQueryRepository).fetchTopTasks(10, "Owner");
    }

    @Test
    void getTopBusyPersonsShouldPassLimitAndFilter() {
        // given
        when(analyticsQueryRepository.fetchTopPersons(5, "Reviewer"))
                .thenReturn(List.of(new TopPersonSummary("p1", "John", "Smith", 7)));

        // when
        List<TopPersonSummary> response = analyticsService.getTopBusyPersons(5, "Reviewer");

        // then
        assertThat(response).hasSize(1);
        assertThat(response.get(0).taskCount()).isEqualTo(7);
        verify(analyticsQueryRepository).fetchTopPersons(5, "Reviewer");
    }
}
