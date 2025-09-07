package com.example.adminservice.mapper;

import com.example.adminservice.dto.DepartmentRequestDTO;
import com.example.adminservice.dto.DepartmentResponseDTO;
import com.example.adminservice.entity.Department;

public class DepartmentMapper {

    public static DepartmentResponseDTO mapToDTO(Department department){

        return DepartmentResponseDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    public static Department mapToEntity(DepartmentRequestDTO requestDTO){

        return Department.builder()
                .name(requestDTO.getName())
                .build();
    }
}

