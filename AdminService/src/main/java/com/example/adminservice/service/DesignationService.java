package com.example.adminservice.service;

import com.example.adminservice.dto.DesignationRequestDTO;
import com.example.adminservice.dto.DesignationResponseDTO;

import java.util.List;

public interface DesignationService {

    DesignationResponseDTO addDesignation(DesignationRequestDTO requestDTO);

    List<DesignationResponseDTO> getAllDesignation();

    DesignationResponseDTO updateDesignation(Long id,DesignationRequestDTO requestDTO);

    void deleteDesignation(Long id);

    DesignationResponseDTO getDesignationById(Long id);

}
