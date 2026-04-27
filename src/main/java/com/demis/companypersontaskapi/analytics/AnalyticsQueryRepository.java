package com.demis.companypersontaskapi.analytics;

import java.util.List;

public interface AnalyticsQueryRepository {

    List<TopTaskSummary> fetchTopTasks(int limit, String participationType);

    List<TopPersonSummary> fetchTopPersons(int limit, String participationType);
}
