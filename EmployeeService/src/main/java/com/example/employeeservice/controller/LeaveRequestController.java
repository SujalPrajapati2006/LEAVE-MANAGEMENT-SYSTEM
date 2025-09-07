package com.example.employeeservice.controller;

import com.example.employeeservice.dto.EmployeeDashboardDTO;
import com.example.employeeservice.dto.LeaveRequestDTO;
import com.example.employeeservice.dto.LeaveRequestResponseDTO;
import com.example.employeeservice.enums.LeaveStatus;
import com.example.employeeservice.service.LeaveRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/leave-request")
@Validated
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<?> applyLeave(@Valid @RequestBody LeaveRequestDTO leaveRequestDTO){

        LeaveRequestResponseDTO responseDTO = leaveRequestService.applyLeave(leaveRequestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateLeaveStatus(@PathVariable Long id, @RequestParam String status){
        LeaveRequestResponseDTO responseDTO = leaveRequestService.updateLeaveStatus(id, status);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status")
    public ResponseEntity<?> getLeaveRequestsByStatus(@RequestParam LeaveStatus status){
        List<LeaveRequestResponseDTO> leaveRequest = leaveRequestService.getLeaveRequestsByStatus(status);
        return new ResponseEntity<>(leaveRequest,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/dashboard/{employeeId}")
    public ResponseEntity<?> getEmployeeDashboard(@PathVariable Long employeeId) {
        EmployeeDashboardDTO dashboard = leaveRequestService.getEmployeeDashboard(employeeId);
        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }

}
