package com.example.adminservice.dto;

import com.example.adminservice.enums.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDTO {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Double salary;

    private String departmentName;

    private String designationName;

    private RoleType role;
}
