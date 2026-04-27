package com.demis.companypersontaskapi.company.hierarchy;

import java.util.List;

public interface HierarchyQueryRepository {

    List<HierarchyModel> fetchHierarchy(
            String companyId,
            List<String> taskIds,
            String personPosition,
            String participationType
    );
}
