package com.eng.homecare.request;

import java.time.LocalDateTime;

public record CredentialsRequestDTO(
        int number,
        String fu,
        LocalDateTime validation_date,
        String type
) {
}
