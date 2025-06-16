package com.eng.homecare.response;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PatientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        LocalDate birthDate,
        String gender,
        LocalDateTime register,
        List<AddressResponseDTO> addresses,
        List<PhoneResponseDTO> phones,
        List<HistoryResponseDTO> histories) {
}