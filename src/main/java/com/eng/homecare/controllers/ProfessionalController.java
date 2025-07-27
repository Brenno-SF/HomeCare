package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.request.AvailabilityRequestDTO;
import com.eng.homecare.request.ProfessionalRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import com.eng.homecare.response.AvailabilityResponseDTO;
import com.eng.homecare.response.ProfessionalResponseDTO;
import com.eng.homecare.services.AppointmentService;
import com.eng.homecare.services.AssessmentService;
import com.eng.homecare.services.AvailabilityService;
import com.eng.homecare.services.ProfessionalServices;
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

    @PostMapping("register")
    public ResponseEntity<ProfessionalResponseDTO> saveProfessional(@RequestBody ProfessionalRequestDTO professionalRequestDTO){
        ProfessionalResponseDTO professionalResponseDTO = professionalServices.create(professionalRequestDTO);
        return ResponseEntity.ok(professionalResponseDTO);
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(professionalServices.update(professionalId,professionalRequestDTO));
    }

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @DeleteMapping("/{professionalId}")
    public ResponseEntity<String> deleteById(@PathVariable Long professionalId, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        professionalServices.removeById(professionalId);
        return ResponseEntity.ok("The professional has been successfully deleted");
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(availabilityService.updateAvailability(availabilityRequestDTO, professionalId));
    }
    //appointment
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/{professionalId}/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long professionalId, @AuthenticationPrincipal JWTUserData professionalData){
        if (!professionalData.id().equals(professionalId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentService.listByProfessionalId(professionalId));
    }
    //assessment
    @GetMapping("/{professionalId}/assessments")
    public ResponseEntity<List<AssessmentResponseDTO>> getAssessment(@PathVariable Long professionalId){

        return ResponseEntity.ok(assessmentService.listByProfessionalId(professionalId));
    }
}
