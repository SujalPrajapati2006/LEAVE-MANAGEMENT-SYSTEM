package com.example.authservice.mapper;

import com.example.authservice.dto.RegistrationRequestDTO;
import com.example.authservice.dto.RegistrationResponseDTO;
import com.example.authservice.dto.UserResponseDTO;
import com.example.authservice.entity.User;

//@Mapper(componentModel = "spring")
public class UserMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    User mapToEntity(RegistrationRequestDTO requestDTO);
//
//    RegistrationResponseDTO mapToDTO(User user);
//
//    // Mapping for user entity to user response DTO
//    UserResponseDTO mapToUserResponseDTO(User user);

    public static User mapToEntity(RegistrationRequestDTO requestDTO) {
        return User.builder()
                .fullName(requestDTO.getFullName())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .phoneNumber(requestDTO.getPhoneNumber())
                .build();
    }

    public static RegistrationResponseDTO mapToDTO(User user) {
        return RegistrationResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roleType(user.getRoleType())
                .build();
    }

    public static UserResponseDTO mapToUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }
}
