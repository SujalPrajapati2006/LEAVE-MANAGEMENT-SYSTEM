package com.example.adminservice.controller;

import com.example.adminservice.dto.DesignationRequestDTO;
import com.example.adminservice.dto.DesignationResponseDTO;
import com.example.adminservice.service.DesignationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/designations")
@Validated
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @PostMapping
    public ResponseEntity<?> addDesignation(@Valid @RequestBody DesignationRequestDTO responseDTO){
        DesignationResponseDTO designation = designationService.addDesignation(responseDTO);

        return new ResponseEntity<>(designation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllDesignation(){

        List<DesignationResponseDTO> responseDTOS = designationService.getAllDesignation();

        return new ResponseEntity<>(responseDTOS,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDesignation(@Positive @PathVariable Long id,@Valid @RequestBody DesignationRequestDTO requestDTO){
        DesignationResponseDTO responseDTO = designationService.updateDesignation(id,requestDTO);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDesignation(@Positive @PathVariable Long id){
        designationService.deleteDesignation(id);
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDesignationById(@Positive @PathVariable Long id){
        DesignationResponseDTO responseDTO = designationService.getDesignationById(id);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
