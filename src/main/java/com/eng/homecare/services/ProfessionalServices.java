package com.eng.homecare.services;

import com.eng.homecare.entities.Professional;
import com.eng.homecare.exceptions.ResourceNotFoundException;
import com.eng.homecare.mapper.ProfessionalMapper;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.repository.UserRepository;
import com.eng.homecare.request.ProfessionalRequestDTO;
import com.eng.homecare.response.ProfessionalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalServices {
    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AvailabilityService availabilityProfessionalService;
    @Autowired
    private EmailService emailService;


    @Transactional
    public ProfessionalResponseDTO create(ProfessionalRequestDTO professionalRequestDTO){
        Professional professional = ProfessionalMapper.toEntity(professionalRequestDTO);

        String encryptedPassword = passwordEncoder.encode(professional.getUser().getPassword());
        professional.getUser().setPassword(encryptedPassword);

        userRepository.save(professional.getUser());
        professional.setRate((float)0);
        professional = professionalRepository.save(professional);

        emailService.sendSimpleEmail(
                professional.getUser().getEmail(),
                "Bem-vindo(a) ao HomeCare!",
                "Olá " + professional.getUser().getName() + ", seu cadastro foi realizado com sucesso!"
        );

        availabilityProfessionalService.createAvailability(professional.getProfessionalId());
        return ProfessionalMapper.toDTO(professional);
    }

    public List<ProfessionalResponseDTO> listAll(){
        List<ProfessionalResponseDTO> professionals = professionalRepository
                    .findAll()
                    .stream()
                    .map(ProfessionalMapper::toDTO)
                    .collect(Collectors.toList());

        return professionals;
    }
    public ProfessionalResponseDTO listById(Long id){
        Professional professional = professionalRepository
                    .findById(id)
                    .orElseThrow(() ->  new ResourceNotFoundException("Professional with ID " + id + " not found"));;

                return ProfessionalMapper.toDTO(professional);
    }
    public void removeById(Long id){
        Professional professional = professionalRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Professional with ID " + id + " not found"));
        professionalRepository.delete(professional);
    }

    public void removeAll(){ professionalRepository.deleteAll();}

    public ProfessionalResponseDTO update(Long id, ProfessionalRequestDTO professionalRequestDTO){
        Professional professionalSaved = professionalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Professional with ID " + id + " not found"));
        professionalSaved.getUser().setName(professionalRequestDTO.name());
        professionalSaved.getUser().setEmail(professionalRequestDTO.email());
        professionalSaved.getUser().setPassword(professionalRequestDTO.password());
//        professionalSaved.getUser().setRegister(ProfessionalRequestDTO.register());
        professionalSaved.getUser().setGender(professionalRequestDTO.gender());
        professionalSaved.getUser().setBirthDate(professionalRequestDTO.birthDate());
//        ProfessionalSaved.getUser().setTypeUser(ProfessionalRequestDTO.typeUser());

        /*
        * Implementar Credentials em outro método
        * */

        userRepository.save(professionalSaved.getUser());
        professionalRepository.save(professionalSaved);
        professionalRepository.flush();

        return ProfessionalMapper.toDTO(professionalSaved);
    }
}
