package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.request.AssessmentRequestDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import com.eng.homecare.services.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("professional/{professionalId}/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService  assessmentService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AssessmentResponseDTO> saveAssessment(@PathVariable Long professionalId, @RequestBody AssessmentRequestDTO dto, @AuthenticationPrincipal JWTUserData patientData){
        return ResponseEntity.ok(assessmentService.createAssessment(professionalId, patientData.id(),dto));
    }

    @GetMapping("{assessmentId}")
    public ResponseEntity<AssessmentResponseDTO>listbyId(@PathVariable Long professionalId) {
        return ResponseEntity.ok(assessmentService.listAssessment(professionalId));
    }

    @DeleteMapping("{assessmentId}")
    public ResponseEntity<String> deleteById(@PathVariable Long assessmentId) {
        assessmentService.deleteAssessment(assessmentId);
        return ResponseEntity.ok("Assessment deleted");
    }
}
