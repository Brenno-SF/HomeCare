package com.eng.homecare.response;


import java.time.LocalDate;
import java.util.List;

public record PatientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        LocalDate birthDate,
        String gender,
        List<AddressResponseDTO> addresses,
        List<PhoneResponseDTO> phones) {
}