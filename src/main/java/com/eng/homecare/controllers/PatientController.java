package com.eng.homecare.controllers;

import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.PatientResponseDTO;
import com.eng.homecare.services.PatientServices;
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
    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long patientId){
        return ResponseEntity.ok(patientServices.listById(patientId));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long patientId){
        patientServices.removeById(patientId);
        return ResponseEntity.ok("The patient has been successfully deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllPatient(){
        patientServices.removeAll();
        return ResponseEntity.ok("All patients have been deleted.");
    }

}
