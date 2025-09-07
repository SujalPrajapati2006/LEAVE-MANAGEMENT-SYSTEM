package com.example.employeeservice.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDTO {

    private Long id;

    private String email;

}
