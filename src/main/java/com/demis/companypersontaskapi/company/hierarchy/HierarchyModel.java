package com.demis.companypersontaskapi.company.hierarchy;

public record HierarchyModel(
        String personId,
        String firstName,
        String lastName,
        String personPosition,
        String taskId,
        String taskTitle,
        String taskDescription,
        String participationType) {
}
