package com.example.adminservice.serviceImpl;

import com.example.adminservice.dto.DepartmentRequestDTO;
import com.example.adminservice.dto.DepartmentResponseDTO;
import com.example.adminservice.entity.Department;
import com.example.adminservice.exception.ResourceNotFoundException;
import com.example.adminservice.mapper.DepartmentMapper;
import com.example.adminservice.repository.DepartmentRepository;
import com.example.adminservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentResponseDTO addDepartment(DepartmentRequestDTO requestDTO) {

        Department department = DepartmentMapper.mapToEntity(requestDTO);
        Department savedDepartment = departmentRepository.save(department);

        return DepartmentMapper.mapToDTO(savedDepartment);
    }

    public List<DepartmentResponseDTO> getAllDepartment() {
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO requestDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResolutionException("Department not found with ID: " + id));

        department.setName(requestDTO.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDTO(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResolutionException("Department not found with ID: " + id));

        departmentRepository.deleteById(id);
    }

    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
        return DepartmentMapper.mapToDTO(department);
    }
}
