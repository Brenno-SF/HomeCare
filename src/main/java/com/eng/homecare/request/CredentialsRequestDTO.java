package com.eng.homecare.request;

import com.eng.homecare.entities.Professional;

import java.time.LocalDateTime;

public record CredentialsRequestDTO(
        Professional professional,
        String number,
        String fu,
        LocalDateTime validation_date,
        String type
) {
}
