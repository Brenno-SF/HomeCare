package com.eng.homecare.controllers;

import com.eng.homecare.entities.Patient;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.PatientResponseDTO;
import com.eng.homecare.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
