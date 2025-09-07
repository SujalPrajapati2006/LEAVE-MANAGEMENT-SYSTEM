package com.example.adminservice.mapper;


import com.example.adminservice.dto.EmployeeResponseDTO;
import com.example.adminservice.entity.Employee;

public class EmployeeMapper {

    public static EmployeeResponseDTO mapToDto(Employee employee) {
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .userId(employee.getUserId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .departmentName(employee.getDepartmentName())
                .designationName(employee.getDesignationName())
                .role(employee.getRole())
                .build();
    }
}
