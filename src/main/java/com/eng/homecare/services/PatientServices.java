package com.eng.homecare.services;

import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.User;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServices {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;

    public Patient save(Patient patient){

        User savedUser = userRepository.save(patient.getUser());
        patient.setUser(savedUser);
        return patientRepository.save(patient);
    }
}
