package com.eng.homecare.response;

import java.time.LocalDateTime;

public record CredentialsResponseDTO(
        Long id,
        String number,
        String fu,
        LocalDateTime validation_date,
        String type
) {
}
