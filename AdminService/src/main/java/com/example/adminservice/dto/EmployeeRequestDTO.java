package com.example.adminservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "DepartmentId is required")
    private Long departmentId;

    @NotNull(message = "DesignationId is required")
    private Long designationId;

    private Double salary;

    @Pattern(regexp = "^(ADMIN|EMPLOYEE|MANAGER)$", message = "Role must be ADMIN, EMPLOYEE, or MANAGER")
    @NotNull(message = "Role is required")
    private String role;


}
