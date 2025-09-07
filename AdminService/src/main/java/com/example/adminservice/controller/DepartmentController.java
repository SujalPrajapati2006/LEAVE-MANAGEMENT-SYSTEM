package com.example.adminservice.controller;

import com.example.adminservice.dto.DepartmentRequestDTO;
import com.example.adminservice.dto.DepartmentResponseDTO;
import com.example.adminservice.service.DepartmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> addDepartment(@Valid @RequestBody DepartmentRequestDTO requestDTO){

        DepartmentResponseDTO responseDTO = departmentService.addDepartment(requestDTO);

        return new ResponseEntity<> (responseDTO,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartment(){

        List<DepartmentResponseDTO> responseDTOS = departmentService.getAllDepartment();

        return new ResponseEntity<>(responseDTOS,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@Positive @PathVariable Long id,@Valid @RequestBody DepartmentRequestDTO requestDTO){
        DepartmentResponseDTO responseDTO = departmentService.updateDepartment(id,requestDTO);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@Positive @PathVariable Long id){
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@Positive @PathVariable Long id){
         DepartmentResponseDTO responseDTO = departmentService.getDepartmentById(id);
         return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

}
