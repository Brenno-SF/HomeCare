package com.eng.homecare.response;

import java.time.LocalDate;


public record CredentialsResponseDTO(
        Long id,
        String number,
        String fu,
        LocalDate validation_date,
        String type
) {
}
