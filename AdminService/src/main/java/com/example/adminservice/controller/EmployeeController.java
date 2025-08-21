package com.example.adminservice.controller;

import com.example.adminservice.dto.*;
import com.example.adminservice.enums.LeaveStatus;
import com.example.adminservice.service.EmployeeService;
import com.example.adminservice.service.LeaveRequestClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Validated
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveRequestClient leaveRequestClient;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) {

        EmployeeResponseDTO responseDTO = employeeService.createEmployee(requestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-employee")
    public ResponseEntity<?> getAllEmployee(){
        List<EmployeeResponseDTO> responseDTOS = employeeService.getAllEmployee();

        return new ResponseEntity<>(responseDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable @Positive Long id,
                                            @Valid @RequestBody EmployeeUpdateDTO updateDTO){
         EmployeeResponseDTO responseDTO = employeeService.updateEmployee(id, updateDTO);

         return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable @Positive Long id){
        EmployeeResponseDTO responseDTO = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by-email")
    public ResponseEntity<?> getEmployeeByEmail(@RequestParam String email) {
        EmployeeResponseDTO responseDTO = employeeService.findEmployeeByEmail(email);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/department")
    public ResponseEntity<?> getEmployeesByDepartment(@RequestParam String departmentName) {
        List<EmployeeResponseDTO> response = employeeService.findEmployeesByDepartmentName(departmentName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/designation")
    public ResponseEntity<?> getEmployeesByDesignation(@RequestParam String designationName){
         List<EmployeeResponseDTO> responseDTOS  = employeeService.findEmployeesByDesignationName(designationName);
         return new ResponseEntity<>(responseDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/summary")
    public ResponseEntity<?> getDashboardSummary() {
        AdminDashboardSummaryDTO dashboardSummaryDTO = employeeService.getDashboardSummary();

        return new ResponseEntity<>(dashboardSummaryDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/leave-request/status/{id}")
    public ResponseEntity<?> changeLeaveStatus(@PathVariable Long id, @RequestParam String status) {
        LeaveRequestResponseDTO updated = leaveRequestClient.updateLeaveStatus(id, status);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/leave-request/view-by-status")
    public ResponseEntity<?> viewLeaveRequestsByStatus(@RequestParam LeaveStatus status){
         List<LeaveRequestResponseDTO> leaveRequest = leaveRequestClient.getLeaveRequestsByStatus(status);
         return new ResponseEntity<>(leaveRequest,HttpStatus.OK);
    }
}