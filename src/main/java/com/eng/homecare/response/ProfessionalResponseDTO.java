package com.eng.homecare.response;

import java.time.LocalDate;
import java.util.List;

public record ProfessionalResponseDTO(
        Long id,
        String name,
        String email,
        LocalDate birthDate,
        String gender,
        List<AddressResponseDTO> addresses,
        List<PhoneResponseDTO> phones,
        String bio,
        String description,
        int rate
) {
}
