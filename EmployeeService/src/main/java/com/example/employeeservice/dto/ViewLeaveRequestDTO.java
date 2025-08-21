package com.example.employeeservice.dto;

import com.example.employeeservice.enums.LeaveStatus;
import com.example.employeeservice.enums.LeaveType;
import com.example.employeeservice.enums.RoleType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ViewLeaveRequestDTO {

    private Long id;

    private Long employeeId;

    private String employeeEmail;

    private LeaveType leaveType;

    private String reason;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double numberOfDays;

    private Double numberOfHours;

    private LocalDate appliedDate;

    private RoleType role;

    private String status;
}
