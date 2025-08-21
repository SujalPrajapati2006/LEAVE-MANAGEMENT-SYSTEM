package com.example.adminservice.dto;

import com.example.adminservice.enums.LeaveStatus;
import com.example.adminservice.enums.LeaveType;
import com.example.adminservice.enums.RoleType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class LeaveRequestResponseDTO {

    private Long id;
    private Long employeeId;
    private String employeeEmail;
    private LeaveType leaveType;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double numberOfDays;
    private Double numberOfHours;
    private String status;
    private RoleType role;
    private LocalDate appliedDate;
}