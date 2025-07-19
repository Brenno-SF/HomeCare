package com.eng.homecare.controllers;

import com.eng.homecare.request.AssessmentRequestDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import com.eng.homecare.services.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("assessment")
@RequiredArgsConstructor
public class AssessmentController {
    private AssessmentService  assessmentService;

    @PostMapping()
    public ResponseEntity<AssessmentResponseDTO> saveAssessment(@RequestBody AssessmentRequestDTO dto){
        return ResponseEntity.ok(assessmentService.createAssessment(dto));
    }

}
