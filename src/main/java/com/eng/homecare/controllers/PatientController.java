package com.eng.homecare.controllers;

import com.eng.homecare.entities.Patient;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.PatientResponseDTO;
import com.eng.homecare.services.PatientServices;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patient")
public class PatientController {
    @Autowired
    private PatientServices patientServices;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> savePatient(@RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientServices.create(patientRequestDTO);
        return ResponseEntity.ok(patientResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatient(){
        return ResponseEntity.ok(patientServices.listAll());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllPatient(){
        patientServices.removeAll();
        return ResponseEntity.ok("All patients have been deleted.");
    }

}
