package com.example.employeeservice.dto;

import com.example.employeeservice.enums.LeaveStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmployeeDashboardDTO {

    private Long employeeId;

    private String employeeEmail;

    private int totalLeaveRequests;

    private Map<LeaveStatus,Long> leaveStatusCounts;

    private double totalLeaveDays;

    private double totalLeaveHours;
}
