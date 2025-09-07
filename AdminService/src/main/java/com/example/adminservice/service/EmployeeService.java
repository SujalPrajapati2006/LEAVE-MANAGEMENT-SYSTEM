package com.example.adminservice.service;

import com.example.adminservice.dto.AdminDashboardSummaryDTO;
import com.example.adminservice.dto.EmployeeRequestDTO;
import com.example.adminservice.dto.EmployeeResponseDTO;
import com.example.adminservice.dto.EmployeeUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);

    List<EmployeeResponseDTO> getAllEmployee();

    EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO updateDTO);

    void deleteEmployee(Long id);

    EmployeeResponseDTO findEmployeeById(Long id);

    EmployeeResponseDTO findEmployeeByEmail(String email);

    List<EmployeeResponseDTO> findEmployeesByDepartmentName(String departmentName);

    List<EmployeeResponseDTO> findEmployeesByDesignationName(String designationName);

    AdminDashboardSummaryDTO getDashboardSummary();

    Page<EmployeeResponseDTO> getAllEmployee(int page, int size);
}
