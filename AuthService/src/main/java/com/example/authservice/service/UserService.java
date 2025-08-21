package com.example.authservice.service;

import com.example.authservice.dto.*;

import java.util.Map;

public interface UserService {

    RegistrationResponseDTO register(RegistrationRequestDTO requestDTO);

    LoginResponseDTO login(String email, String password,String deviceInfo);

    UserResponseDTO getUserById(Long id);

    Map<String, String> refreshToken(String authHeader);

    void logout(String authHeader,String refreshToken);

    void changePassword(String authHeader, ChangePasswordRequestDTO requestDTO);

    Map<String,String> forgotPassword(ForgotPasswordRequestDTO requestDTO);

    void resetPassword(ResetPasswordRequestDTO requestDTO);
}
