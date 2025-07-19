package com.eng.homecare.mapper;

import com.eng.homecare.entities.Assessment;
import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.request.AssessmentRequestDTO;
import com.eng.homecare.request.AvailabilityRequestDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import com.eng.homecare.response.AvailabilityResponseDTO;

public class AssessmentMapper {
    public static Assessment toEntity(AssessmentRequestDTO dto, Professional professional, Patient patient) {
        Assessment assessment = new Assessment();
        assessment.setProfessional(professional);
        assessment.setPatient(patient);
        assessment.setTitle(dto.title());
        assessment.setDescription(dto.description());
        assessment.setStars(dto.stars());
        return assessment;
    }

    public static AssessmentResponseDTO toDTO(Assessment assessment) {
        return new AssessmentResponseDTO(
                assessment.getProfessional().getProfessionalId(),
                assessment.getPatient().getPatientId(),
                assessment.getTitle(),
                assessment.getDescription(),
                assessment.getStars()
        );
    }
}
