package com.eng.homecare.mapper;

import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.User;
import com.eng.homecare.enums.TypeUser;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.PatientResponseDTO;

public class PatientMapper {

    public static Patient toEntity(PatientRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setBirthDate(dto.birthDate());
        user.setGender(dto.gender());
        user.setTypeUser(TypeUser.Patient);

        Patient patient = new Patient();
        patient.setCpf(dto.cpf());
        patient.setUser(user);

        return patient;
    }


    public static PatientResponseDTO toDTO(Patient patient) {
        User user = patient.getUser();
        return new PatientResponseDTO(user.getUserId(), user.getName(), user.getEmail(), patient.getCpf(), user.getBirthDate(), user.getGender());
    }
}
