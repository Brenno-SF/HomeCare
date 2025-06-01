package com.eng.homecare.request;

import java.time.LocalDate;

public record PatientRequestDTO(
        String name,
        String email,
        String password,
        String cpf,
        LocalDate birthDate,
        String gender) {
}