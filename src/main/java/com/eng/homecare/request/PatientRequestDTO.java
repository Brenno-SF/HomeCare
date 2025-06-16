package com.eng.homecare.request;

import java.time.LocalDate;
import java.util.List;

public record PatientRequestDTO(
                                String name,
                                String email,
                                String password,
                                String cpf,
                                LocalDate birthDate,
                                String gender,
                                List<AddressRequestDTO> addresses,
                                List<PhoneRequestDTO> phones,
                                List<HistoryRequestDTO> histories
                                ) {
}