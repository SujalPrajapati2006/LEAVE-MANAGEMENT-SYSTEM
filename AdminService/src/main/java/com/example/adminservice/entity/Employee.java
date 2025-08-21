package com.example.adminservice.entity;

import com.example.adminservice.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(unique = true)
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private Double salary;

    @Column(nullable = false)
    private String departmentName;

    @Column(nullable = false)
    private String designationName;

    @Enumerated(EnumType.STRING)
    private RoleType role;
}
