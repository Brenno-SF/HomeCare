package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.request.AddressRequestDTO;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.AddressResponseDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import com.eng.homecare.response.PatientResponseDTO;
import com.eng.homecare.services.AddressService;
import com.eng.homecare.services.AppointmentService;
import com.eng.homecare.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    private AddressService addressService;

    @Transactional
    @PostMapping("register")
    public ResponseEntity<PatientResponseDTO> savePatient(@RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO patientResponseDTO = patientServices.create(patientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);
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
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientResponseDTO> updatePatientById(@PathVariable Long patientId, @RequestBody PatientRequestDTO patientRequestDTO, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)){
            throw new ForbiddenAccessException("You cannot update another patient.");
        }
        return ResponseEntity.ok(patientServices.update(patientId,patientRequestDTO));
    }

    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Void> deletePatientById(@PathVariable Long patientId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)){
            throw new ForbiddenAccessException("You cannot delete another patient.");
        }
        patientServices.removeById(patientId);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping
//    public ResponseEntity<String> deleteAllPatient(){
//        patientServices.removeAll();
//        return ResponseEntity.ok("All patients have been deleted.");
//    }

    //appointments
    @GetMapping("/{patientId}/appointment")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long patientId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's appointments.");
        }
        return ResponseEntity.ok(appointmentService.listByPatientId(patientId));
    }
    //address
    @PutMapping("/{patientId}/address/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long patientId, @PathVariable Long addressId, @RequestBody AddressRequestDTO addressRequestDTO, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's address.");

        }
        return ResponseEntity.ok(addressService.update(addressId,addressRequestDTO,patientId));
    }

}
