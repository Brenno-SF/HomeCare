package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.request.AddressRequestDTO;
import com.eng.homecare.request.HistoryRequestDTO;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.request.PhoneRequestDTO;
import com.eng.homecare.response.*;
import com.eng.homecare.services.*;
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
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private HistoryService historyService;

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
    @PostMapping("/{patientId}/address/add")
    public ResponseEntity<AddressResponseDTO> addAddress(@PathVariable Long patientId, @RequestBody AddressRequestDTO addressRequestDTO, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's address.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(addressRequestDTO,patientId));
    }
    @GetMapping("/{patientId}/address")
    public ResponseEntity<List<AddressResponseDTO>> getAddressByProfessionalId(@PathVariable Long patientId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's address.");
        }
        return ResponseEntity.ok(addressService.listAllByUserId(patientId));
    }
    @GetMapping("/{patientId}/address/{addressId}")
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable Long patientId, @PathVariable Long addressId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's address.");

        }
        return ResponseEntity.ok(addressService.listById(addressId,patientId));
    }
    @PutMapping("/{patientId}/address/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long patientId, @PathVariable Long addressId, @RequestBody AddressRequestDTO addressRequestDTO, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's address.");

        }
        return ResponseEntity.ok(addressService.update(addressId,addressRequestDTO,patientId));
    }
    @DeleteMapping("/{patientId}/address/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long patientId, @PathVariable Long addressId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's address.");

        }
        addressService.delete(addressId,patientId);
        return ResponseEntity.noContent().build();
    }
    //phone
    @PostMapping("/{patientId}/phone/add")
    public ResponseEntity<PhoneResponseDTO> addPhone(@PathVariable Long patientId, @RequestBody PhoneRequestDTO phoneRequestDTO, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's phone.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(phoneService.create(phoneRequestDTO,patientId));
    }
    @GetMapping("/{patientId}/phone/{phoneId}")
    public ResponseEntity<PhoneResponseDTO> getPhone(@PathVariable Long patientId, @PathVariable Long phoneId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's phone.");

        }
        return ResponseEntity.ok(phoneService.listById(phoneId,patientId));
    }
    @GetMapping("/{patientId}/phone")
    public ResponseEntity<List<PhoneResponseDTO>> getPhoneByProfessionalId(@PathVariable Long patientId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's phone.");
        }
        return ResponseEntity.ok(phoneService.listAllByUserId(patientId));
    }
    @PutMapping("/{patientId}/phone/{phoneId}")
    public ResponseEntity<PhoneResponseDTO> updatePhone(@PathVariable Long patientId, @PathVariable Long phoneId, @RequestBody PhoneRequestDTO phoneRequestDTO, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's phone.");
        }
        return ResponseEntity.ok(phoneService.update(phoneId,phoneRequestDTO,patientId));
    }
    @DeleteMapping("/{patientId}/phone/{phoneId}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long patientId, @PathVariable Long phoneId, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's phone.");
        }
        phoneService.delete(phoneId,patientId);
        return ResponseEntity.noContent().build();
    }

    //history
    @PostMapping("/{patientId}/history/add")
    public ResponseEntity<HistoryResponseDTO> addHistory(@PathVariable Long patientId, @RequestBody HistoryRequestDTO historyRequestDTO, @AuthenticationPrincipal JWTUserData patientData){
        if (!patientData.id().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot access another patient's history.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(historyService.create(historyRequestDTO,patientId));
    }

}
