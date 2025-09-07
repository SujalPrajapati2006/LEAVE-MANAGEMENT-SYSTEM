package com.example.employeeservice.service;

import com.example.employeeservice.dto.EmployeeDashboardDTO;
import com.example.employeeservice.dto.LeaveRequestDTO;
import com.example.employeeservice.dto.LeaveRequestResponseDTO;
import com.example.employeeservice.dto.ViewLeaveRequestDTO;
import com.example.employeeservice.enums.LeaveStatus;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequestResponseDTO applyLeave(LeaveRequestDTO requestDTO);
    LeaveRequestResponseDTO updateLeaveStatus(Long id, String status);

    List<LeaveRequestResponseDTO> getLeaveRequestsByStatus(LeaveStatus status);

    EmployeeDashboardDTO getEmployeeDashboard(Long employeeId);

}
