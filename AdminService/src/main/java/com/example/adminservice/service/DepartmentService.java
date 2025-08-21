package com.example.adminservice.service;

import com.example.adminservice.dto.DepartmentRequestDTO;
import com.example.adminservice.dto.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentResponseDTO addDepartment(DepartmentRequestDTO requestDTO);

    List<DepartmentResponseDTO> getAllDepartment();

    DepartmentResponseDTO updateDepartment(Long id,DepartmentRequestDTO requestDTO);

    void deleteDepartment(Long id);

    DepartmentResponseDTO getDepartmentById(Long id);

}
