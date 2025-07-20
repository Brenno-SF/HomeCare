package com.eng.homecare.request;

public record AssessmentRequestDTO(
        String title,
        String description,
        float stars
) {
}
