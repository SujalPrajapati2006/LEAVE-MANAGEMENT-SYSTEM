package com.example.employeeservice.serviceImpl;

import com.example.employeeservice.FeignClient.AdminServiceClient;
import com.example.employeeservice.dto.*;
import com.example.employeeservice.entity.LeaveRequest;
import com.example.employeeservice.enums.LeaveStatus;
import com.example.employeeservice.enums.RoleType;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.mapper.LeaveRequestMapper;
import com.example.employeeservice.repository.LeaveRequestRepository;
import com.example.employeeservice.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private AdminServiceClient adminServiceClient;

    public LeaveRequestResponseDTO applyLeave(LeaveRequestDTO requestDTO) {

        EmployeeResponseDTO responseDTO = adminServiceClient.getEmployeeById(requestDTO.getEmployeeId());


        LocalDate startDate = requestDTO.getStartDate();
        LocalDate endDate = requestDTO.getEndDate();

        double numberOfDays = calculateWorkingDays(startDate, endDate);
        double numberOfHours = numberOfDays * 8.0;

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employeeId(responseDTO.getId())
                .employeeEmail(responseDTO.getEmail())
                .leaveType(requestDTO.getLeaveType())
                .reason(requestDTO.getReason())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .role(RoleType.valueOf(requestDTO.getRole().toUpperCase()))
                .appliedDate(LocalDate.now())
                .status(LeaveStatus.PENDING)
                .numberOfDays(numberOfDays)
                .numberOfHours(numberOfHours)
                .build();

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        return LeaveRequestMapper.mapToDTO(saved);
    }

    public LeaveRequestResponseDTO updateLeaveStatus(Long id, String status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with ID: " + id));

        LeaveStatus newStatus;
        try {
            newStatus = LeaveStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new IllegalStateException("Leave request is already processed.");
        }

        leaveRequest.setStatus(newStatus);
        LeaveRequest updated = leaveRequestRepository.save(leaveRequest);

        return LeaveRequestMapper.mapToDTO(updated);
    }

    public List<LeaveRequestResponseDTO> getLeaveRequestsByStatus(LeaveStatus status) {
        List<LeaveRequest> requests = leaveRequestRepository.findByStatus(status);

        return requests.stream()
                .map(LeaveRequestMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDashboardDTO getEmployeeDashboard(Long employeeId){
        List<LeaveRequest> requests = leaveRequestRepository.findByEmployeeId(employeeId);

        if (requests.isEmpty()) {
            throw new ResourceNotFoundException("No leave requests found for employee ID: " + employeeId);
        }

        Map<LeaveStatus, Long> statusCounts = requests.stream()
                .collect(Collectors.groupingBy(LeaveRequest::getStatus, Collectors.counting()));

        double totalDays = requests.stream().mapToDouble(LeaveRequest::getNumberOfDays).sum();
        double totalHours = requests.stream().mapToDouble(LeaveRequest::getNumberOfHours).sum();

        return EmployeeDashboardDTO.builder()
                .employeeId(employeeId)
                .employeeEmail(requests.get(0).getEmployeeEmail())
                .totalLeaveRequests(requests.size())
                .leaveStatusCounts(statusCounts)
                .totalLeaveDays(totalDays)
                .totalLeaveHours(totalHours)
                .build();
    }

    private long calculateWorkingDays(LocalDate start, LocalDate end) {
        long workingDays = 0;

        LocalDate currentDate = start;

        while (!currentDate.isAfter(end)) {
            if (currentDate.getDayOfWeek() != java.time.DayOfWeek.SATURDAY &&
                    currentDate.getDayOfWeek() != java.time.DayOfWeek.SUNDAY) {
                workingDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return workingDays;
    }

}
