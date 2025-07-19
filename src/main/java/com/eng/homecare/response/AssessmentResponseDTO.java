package com.eng.homecare.response;

public record AssessmentResponseDTO(long professionalId,
                                    long patientId,
                                    String title,
                                    String description,
                                    float stars) {
}
