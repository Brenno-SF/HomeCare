package com.eng.homecare.response;

import java.time.LocalDateTime;

public record CredentialsResponseDTO(
        Long id,
        Long professional_id,
        int number,
        String fu,
        LocalDateTime validation_date,
        String type
) {
}
