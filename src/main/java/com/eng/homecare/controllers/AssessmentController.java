package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.request.AssessmentRequestDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import com.eng.homecare.services.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("professional/{professionalId}/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AssessmentResponseDTO> saveAssessment(@PathVariable Long professionalId, @RequestBody AssessmentRequestDTO dto, @AuthenticationPrincipal JWTUserData patientData){
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentService.createAssessment(professionalId, patientData.id(),dto));
    }

    @GetMapping("{assessmentId}")
    public ResponseEntity<AssessmentResponseDTO>listbyId(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(assessmentService.listAssessment(assessmentId));
    }

    @PatchMapping("/assessments/{assessmentId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AssessmentResponseDTO> patchAssessment(@PathVariable Long assessmentId,@RequestBody AssessmentRequestDTO dto, @AuthenticationPrincipal JWTUserData patientData) {
        AssessmentResponseDTO existingAssessment = assessmentService.listAssessment(assessmentId);
        if (!patientData.id().equals(existingAssessment.patientId())){
            throw new ForbiddenAccessException("You cannot update another patient's assessment");
        }
        AssessmentResponseDTO response = assessmentService.patchAssessment(assessmentId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{assessmentId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Void> deleteById(@PathVariable Long assessmentId, @AuthenticationPrincipal JWTUserData patientData) {
        AssessmentResponseDTO existingAssessment = assessmentService.listAssessment(assessmentId);
        if (!patientData.id().equals(existingAssessment.patientId())){
            throw new ForbiddenAccessException("You cannot delete another patient's assessment");
        }
        assessmentService.deleteAssessment(assessmentId);
        return ResponseEntity.noContent().build();
    }
}
