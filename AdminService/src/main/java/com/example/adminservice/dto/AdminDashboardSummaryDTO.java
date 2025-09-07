package com.example.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDashboardSummaryDTO {
    private Long totalEmployees;
    private Long totalDepartments;
    private Map<String, Long> employeesPerDepartment;
}
