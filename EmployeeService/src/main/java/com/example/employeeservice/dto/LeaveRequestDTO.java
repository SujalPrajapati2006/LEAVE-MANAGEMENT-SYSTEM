package com.example.employeeservice.dto;


import com.example.employeeservice.enums.LeaveStatus;
import com.example.employeeservice.enums.LeaveType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaveRequestDTO {

    @NotNull(message = "Employee Id is required ")
    private Long employeeId;

    @NotNull(message = "Leave Type is required")
    private LeaveType leaveType;

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Column(nullable = false)
    private Double numberOfDays = 0.0;

    @Column(nullable = false)
    private Double numberOfHours = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status = LeaveStatus.PENDING;

    @Pattern(regexp = "^(ADMIN|EMPLOYEE|MANAGER)$", message = "Role must be ADMIN, EMPLOYEE, or MANAGER")
    @NotNull(message = "Role is required")
    private String role;

    @Column(nullable = false)
    private LocalDate appliedDate = LocalDate.now();

}
