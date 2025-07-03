package com.eng.homecare.request;

import java.time.LocalDate;
import java.util.List;

public record ProfessionalRequestDTO(
        String name,
        String email,
        String password,
        LocalDate birthDate,
        String gender,
        List<AddressRequestDTO> addresses,
        List<PhoneRequestDTO> phones,
        List<CredentialsRequestDTO> credentials,
        String bio,
        String description,
        Float rate
){
}
