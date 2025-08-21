package com.example.employeeservice.mapper;

import com.example.employeeservice.dto.LeaveRequestResponseDTO;
import com.example.employeeservice.entity.LeaveRequest;
import java.time.LocalDate;

public class LeaveRequestMapper {

    public static LeaveRequestResponseDTO mapToDTO(LeaveRequest leaveRequest){

        return LeaveRequestResponseDTO.builder()
                .id(leaveRequest.getId())
                .employeeId(leaveRequest.getEmployeeId())
                .employeeEmail(leaveRequest.getEmployeeEmail())
                .leaveType(leaveRequest.getLeaveType())
                .reason(leaveRequest.getReason())
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .numberOfHours(leaveRequest.getNumberOfHours())
                .numberOfDays(leaveRequest.getNumberOfDays())
                .appliedDate(LocalDate.now())
                .role(leaveRequest.getRole())
                .status(leaveRequest.getStatus())
                .build();
    }
}
