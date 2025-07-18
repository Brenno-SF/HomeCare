package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.response.PatientResponseDTO;
import com.eng.homecare.services.AppointmentService;
import com.eng.homecare.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patient")
public class PatientController {
    @Autowired
    private PatientServices patientServices;
    @Autowired
    private AppointmentService appointmentService;

    @Transactional
    @PostMapping("register")
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

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> updatePatientById(@PathVariable Long patientId, @RequestBody PatientRequestDTO patientRequestDTO){

        return ResponseEntity.ok(patientServices.update(patientId,patientRequestDTO));
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

    //appointments
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/{patientId}/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long patientId){
        JWTUserData userData = (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userData.id().equals(patientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentService.listByPatientId(patientId));
    }

}
