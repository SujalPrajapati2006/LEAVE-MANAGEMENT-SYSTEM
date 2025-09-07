package com.example.adminservice.serviceImpl;

import com.example.adminservice.dto.*;
import com.example.adminservice.entity.Department;
import com.example.adminservice.entity.Designation;
import com.example.adminservice.entity.Employee;
import com.example.adminservice.enums.RoleType;
import com.example.adminservice.exception.ResourceNotFoundException;
import com.example.adminservice.mapper.EmployeeMapper;
import com.example.adminservice.repository.DepartmentRepository;
import com.example.adminservice.repository.DesignationRepository;
import com.example.adminservice.repository.EmployeeRepository;
import com.example.adminservice.service.AuthServiceClient;
import com.example.adminservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {

        UserResponseDTO userResponse = authServiceClient.getUserById(requestDTO.getUserId());

        if (userResponse == null) {
            throw new ResourceNotFoundException("User not found in Auth Service");
        }

        Department department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Designation designation = designationRepository.findById(requestDTO.getDesignationId())
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        Employee employee = Employee.builder()
                .userId(userResponse.getId())
                .fullName(userResponse.getFullName())
                .email(userResponse.getEmail())
                .phoneNumber(userResponse.getPhoneNumber())
                .salary(requestDTO.getSalary())
                .departmentName(department.getName())
                .designationName(designation.getName())
                .role(RoleType.valueOf(requestDTO.getRole().toUpperCase()))
                .build();

        return EmployeeMapper.mapToDto(employeeRepository.save(employee));
    }

    public List<EmployeeResponseDTO> getAllEmployee() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateDTO updateDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not find with ID: "+ id));

        Department department = departmentRepository.findById(updateDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Designation designation = designationRepository.findById(updateDTO.getDesignationId())
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        employee.setFullName(updateDTO.getFullName());
        employee.setEmail(updateDTO.getEmail());
        employee.setPhoneNumber(updateDTO.getPhoneNumber());
        employee.setDepartmentName(department.getName());
        employee.setDesignationName(designation.getName());
        Employee updatedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToDto(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not find with ID: "+ id));

        employeeRepository.deleteById(id);
    }

    public EmployeeResponseDTO findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not find with ID: "+ id));

        return EmployeeMapper.mapToDto(employee);
    }

    public EmployeeResponseDTO findEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));

        return EmployeeMapper.mapToDto(employee);
    }

    public List<EmployeeResponseDTO> findEmployeesByDepartmentName(String departmentName) {
        List<Employee> employees = employeeRepository.findByDepartmentNameIgnoreCase(departmentName);

        return employees.stream()
                .map(EmployeeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDTO> findEmployeesByDesignationName(String designationName) {
        List<Employee> employees = employeeRepository.findByDesignationNameIgnoreCase(designationName);
        return employees.stream()
                .map(EmployeeMapper::mapToDto)
                .collect(Collectors.toList());
     }

    public AdminDashboardSummaryDTO getDashboardSummary() {
        long totalEmployees = employeeRepository.count();
        long totalDepartments = departmentRepository.count();

        List<DepartmentEmployeeCount> departmentCounts = employeeRepository.countEmployeesPerDepartment();

        Map<String, Long> employeesPerDepartment = new HashMap<>();
        for (DepartmentEmployeeCount dept : departmentCounts) {
            employeesPerDepartment.put(dept.getDepartmentName(), dept.getEmployeeCount());
        }

        return AdminDashboardSummaryDTO.builder()
                .totalEmployees(totalEmployees)
                .totalDepartments(totalDepartments)
                .employeesPerDepartment(employeesPerDepartment)
                .build();
    }

}
