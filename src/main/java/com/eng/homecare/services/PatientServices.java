package com.eng.homecare.services;

import com.eng.homecare.entities.Patient;
import com.eng.homecare.mapper.PatientMapper;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.repository.UserRepository;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.response.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServices {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public PatientResponseDTO create(PatientRequestDTO patientRequestDTO){
        Patient patient = PatientMapper.toEntity(patientRequestDTO);

        String encryptedPassword = passwordEncoder.encode(patient.getUser().getPassword());
        patient.getUser().setPassword(encryptedPassword);

        userRepository.save(patient.getUser());
        patient = patientRepository.save(patient);

        emailService.sendSimpleEmail(
                patient.getUser().getEmail(),
                "Bem-vindo(a) ao HomeCare!",
                "Olá " + patient.getUser().getName() + ", seu cadastro foi realizado com sucesso!"
        );

        return PatientMapper.toDTO(patient);

    }

    public List<PatientResponseDTO> listAll(){
        List<PatientResponseDTO> patients = patientRepository.findAll().stream().map(PatientMapper::toDTO).collect(Collectors.toList());
        return patients;
    }
    public PatientResponseDTO listById(Long id){
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found"));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO update(Long id, PatientRequestDTO patientRequestDTO){
        Patient patientSaved = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found"));

        patientSaved.getUser().setName(patientRequestDTO.name());
        patientSaved.getUser().setEmail(patientRequestDTO.email());
        patientSaved.getUser().setPassword(patientRequestDTO.password());
//        patientSaved.getUser().setRegister(patientRequestDTO.register());
        patientSaved.getUser().setGender(patientRequestDTO.gender());
        patientSaved.getUser().setBirthDate(patientRequestDTO.birthDate());
//        patientSaved.getUser().setTypeUser(patientRequestDTO.typeUser());

        userRepository.save(patientSaved.getUser());
        patientRepository.save(patientSaved);
        patientRepository.flush();
        return PatientMapper.toDTO(patientSaved);
    }

    public void removeById(Long id){
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found"));
        patientRepository.delete(patient);
    }

    public void removeAll(){
        patientRepository.deleteAll();
    }
}
