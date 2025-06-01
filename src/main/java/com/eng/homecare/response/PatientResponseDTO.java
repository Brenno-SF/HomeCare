package com.eng.homecare.response;

import java.time.LocalDate;

public record PatientResponseDTO(
        Integer id,
        String name,
        String email,
        String cpf,
        LocalDate birthDate,
        String gender) {
}