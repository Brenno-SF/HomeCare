package com.eng.homecare.services;

import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.User;
import com.eng.homecare.mapper.PatientMapper;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.repository.UserRepository;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServices {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;

    public PatientResponseDTO create(PatientRequestDTO patientRequestDTO){
        Patient patient = PatientMapper.toEntity(patientRequestDTO);

        userRepository.save(patient.getUser());
        patient = patientRepository.save(patient);

        return PatientMapper.toDTO(patient);

    }
}
