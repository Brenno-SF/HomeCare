package com.eng.homecare.request;

public record AssessmentRequestDTO(
        long professionalId,
        long patientId,
        String title,
        String description
) {
}
