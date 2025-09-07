package com.example.adminservice.mapper;

import com.example.adminservice.dto.DesignationRequestDTO;
import com.example.adminservice.dto.DesignationResponseDTO;
import com.example.adminservice.entity.Designation;

public class DesignationMapper {

    public static DesignationResponseDTO mapToDTO(Designation designation){

        return DesignationResponseDTO.builder()
                .id(designation.getId())
                .name(designation.getName())
                .build();
    }

    public static Designation mapToEntity(DesignationRequestDTO requestDTO){

        return Designation.builder()
                .name(requestDTO.getName())
                .build();
    }
}
