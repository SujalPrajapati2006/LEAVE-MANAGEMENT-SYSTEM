package com.example.adminservice.service;

import com.example.adminservice.dto.UserResponseDTO;
import com.example.adminservice.security.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE",configuration = FeignConfig.class)
public interface AuthServiceClient {

    @GetMapping("/auth/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

}
