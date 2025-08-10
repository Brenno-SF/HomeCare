package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.request.AddressRequestDTO;
import com.eng.homecare.request.AvailabilityRequestDTO;
import com.eng.homecare.request.ProfessionalRequestDTO;
import com.eng.homecare.response.*;
import com.eng.homecare.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("professional")
public class ProfessionalController {
    @Autowired
    private ProfessionalServices professionalServices;
    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private AddressService addressService;

    @PostMapping("register")
    public ResponseEntity<ProfessionalResponseDTO> saveProfessional(@RequestBody ProfessionalRequestDTO professionalRequestDTO){
        ProfessionalResponseDTO professionalResponseDTO = professionalServices.create(professionalRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(professionalResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<ProfessionalResponseDTO>> getAllProfessional(){
        return ResponseEntity.ok(professionalServices.listAll());
    }
    @GetMapping("/{professionalId}")
    public ResponseEntity<ProfessionalResponseDTO> getProfessionalById(@PathVariable Long professionalId){
        return ResponseEntity.ok(professionalServices.listById(professionalId));
    }

    @PutMapping("/{professionalId}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalResponseDTO> updateProfessionalById(@PathVariable Long professionalId, @RequestBody ProfessionalRequestDTO professionalRequestDTO,@AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)){
            throw new ForbiddenAccessException("You cannot update another user.");
        }
        return ResponseEntity.ok(professionalServices.update(professionalId,professionalRequestDTO));
    }

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @DeleteMapping("/{professionalId}")
    public ResponseEntity<String> deleteById(@PathVariable Long professionalId, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)){
            throw new ForbiddenAccessException("You cannot delete another user.");
        }
        professionalServices.removeById(professionalId);
        return ResponseEntity.noContent().build();
    }
//    @DeleteMapping
//    public ResponseEntity<String> deleteAllProfessional(){
//        professionalServices.removeAll();
//        return ResponseEntity.ok("All professionals has been deleted.");
//    }
    //availability
    @PutMapping("/{professionalId}/updateAvailability")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<AvailabilityResponseDTO> updateAvailabilityProfessional(@PathVariable Long professionalId, @RequestBody AvailabilityRequestDTO availabilityRequestDTO, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)){
            throw new ForbiddenAccessException("You cannot access another professional's availability.");
        }
        return ResponseEntity.ok(availabilityService.updateAvailability(availabilityRequestDTO, professionalId));
    }
    //appointment
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/{professionalId}/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long professionalId, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)){
            throw new ForbiddenAccessException("You cannot access another professional's appointments.");
        }
        return ResponseEntity.ok(appointmentService.listByProfessionalId(professionalId));
    }
    //assessment
    @GetMapping("/{professionalId}/assessments")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessment(@PathVariable Long professionalId){
        return ResponseEntity.ok(assessmentService.listByProfessionalId(professionalId));
    }

    //address
    @PostMapping("/{professionalId}/address/add")
    public ResponseEntity<AddressResponseDTO> addAddress(@PathVariable Long professionalId, @RequestBody AddressRequestDTO addressRequestDTO, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)) {
            throw new ForbiddenAccessException("You cannot access another professional's address.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(addressRequestDTO,professionalId));
    }
    @GetMapping("/{professionalId}/address/{addressId}")
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable Long professionalId, @PathVariable Long addressId,  @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)) {
            throw new ForbiddenAccessException("You cannot access another professional's address.");

        }
        return ResponseEntity.ok(addressService.listById(addressId,professionalId));
    }
    @GetMapping("/{professionalId}/address")
    public ResponseEntity<List<AddressResponseDTO>> getAddressByProfessionalId(@PathVariable Long professionalId, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)) {
            throw new ForbiddenAccessException("You cannot access another professional's address.");
        }
        return ResponseEntity.ok(addressService.listAllByUserId(professionalId));
    }
    @PutMapping("/{professionalId}/address/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long professionalId, @PathVariable Long addressId, @RequestBody AddressRequestDTO addressRequestDTO, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)) {
            throw new ForbiddenAccessException("You cannot access another professional's address.");

        }
        return ResponseEntity.ok(addressService.update(addressId,addressRequestDTO,professionalId));
    }
    @DeleteMapping("/{professionalId}/address/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long professionalId, @PathVariable Long addressId, @RequestBody AddressRequestDTO addressRequestDTO, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)) {
            throw new ForbiddenAccessException("You cannot access another professional's address.");

        }
        addressService.delete(addressId,professionalId);
        return ResponseEntity.noContent().build();
    }
}
