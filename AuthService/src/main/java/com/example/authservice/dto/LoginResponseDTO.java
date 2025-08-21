package com.example.authservice.dto;

import com.example.authservice.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String email;
    private String fullName;
    private RoleType roleType;
    private String refreshToken;
    private Long expiration;
}

