package com.example.employeeservice.repository;

import com.example.employeeservice.entity.LeaveRequest;
import com.example.employeeservice.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {

    List<LeaveRequest> findByStatus(LeaveStatus status);

    List<LeaveRequest> findByEmployeeId(Long employeeId);

}
