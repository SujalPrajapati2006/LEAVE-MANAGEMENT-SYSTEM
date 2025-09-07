package com.example.adminservice.repository;

import com.example.adminservice.dto.DepartmentEmployeeCount;
import com.example.adminservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartmentNameIgnoreCase(String departmentName);

    List<Employee> findByDesignationNameIgnoreCase(String designationName);

    @Query(value = "SELECT department_name AS departmentName, COUNT(*) AS employeeCount FROM employee GROUP BY department_name", nativeQuery = true)
    List<DepartmentEmployeeCount> countEmployeesPerDepartment();
}
