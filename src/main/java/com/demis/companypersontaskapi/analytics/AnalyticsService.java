package com.demis.companypersontaskapi.analytics;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AnalyticsQueryRepository analyticsQueryRepository;

    public List<TopTaskSummary> getTopMultiUserTasks(int limit, String participationType) {
        int safeLimit = limit > 0 ? limit : 10;
        return analyticsQueryRepository.fetchTopTasks(safeLimit, participationType);
    }

    public List<TopPersonSummary> getTopBusyPersons(int limit, String participationType) {
        int safeLimit = limit > 0 ? limit : 10;
        return analyticsQueryRepository.fetchTopPersons(safeLimit, participationType);
    }
}
