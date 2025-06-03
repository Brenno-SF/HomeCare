package com.eng.homecare.request;

import com.eng.homecare.entities.Professional;

import java.time.LocalDate;

public record CredentialsRequestDTO(
        Professional professional,
        String number,
        String fu,
        LocalDate validation_date,
        String type
) {
}
