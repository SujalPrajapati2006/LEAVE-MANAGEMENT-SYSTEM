package com.example.employeeservice.FeignClient;


import com.example.employeeservice.dto.EmployeeResponseDTO;
import com.example.employeeservice.security.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ADMIN-SERVICE", configuration = FeignConfig.class)
public interface AdminServiceClient {

    @GetMapping("/employees/{id}")
    EmployeeResponseDTO getEmployeeById(@PathVariable("id") Long id);

}
