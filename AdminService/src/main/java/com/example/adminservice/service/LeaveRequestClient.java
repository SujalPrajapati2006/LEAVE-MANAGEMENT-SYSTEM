package com.example.adminservice.service;

import com.example.adminservice.dto.LeaveRequestResponseDTO;
import com.example.adminservice.enums.LeaveStatus;
import com.example.adminservice.security.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "EMPLOYEE-SERVICE",configuration = FeignConfig.class)
public interface LeaveRequestClient {

    @PutMapping("/leave-request/update-status/{id}")
    LeaveRequestResponseDTO updateLeaveStatus(@PathVariable("id") Long id, @RequestParam("status") String status);

    @GetMapping("/leave-request/status")
    List<LeaveRequestResponseDTO> getLeaveRequestsByStatus(@RequestParam("status") LeaveStatus status);

}
