package com.example.adminservice.serviceImpl;

import com.example.adminservice.dto.DesignationRequestDTO;
import com.example.adminservice.dto.DesignationResponseDTO;
import com.example.adminservice.entity.Designation;
import com.example.adminservice.exception.ResourceNotFoundException;
import com.example.adminservice.mapper.DesignationMapper;
import com.example.adminservice.repository.DesignationRepository;
import com.example.adminservice.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    public DesignationResponseDTO addDesignation(DesignationRequestDTO requestDTO) {
        Designation designation = DesignationMapper.mapToEntity(requestDTO);
        Designation savedDesignation = designationRepository.save(designation);

        return DesignationMapper.mapToDTO(savedDesignation);
    }

    public List<DesignationResponseDTO> getAllDesignation() {
          return designationRepository.findAll()
                .stream()
                .map(DesignationMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public DesignationResponseDTO updateDesignation(Long id, DesignationRequestDTO requestDTO) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found with ID: "+id));

        designation.setName(requestDTO.getName());
        Designation updatedDesignation = designationRepository.save(designation);
        return DesignationMapper.mapToDTO(updatedDesignation);
    }

    public void deleteDesignation(Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResolutionException("Designation not found with ID: " + id));

         designationRepository.deleteById(id);
    }

    public DesignationResponseDTO getDesignationById(Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found with ID: " + id));
        return DesignationMapper.mapToDTO(designation);
    }
}
