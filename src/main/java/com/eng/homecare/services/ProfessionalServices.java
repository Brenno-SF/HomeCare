package com.eng.homecare.services;

import com.eng.homecare.entities.Professional;
import com.eng.homecare.mapper.ProfessionalMapper;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.repository.UserRepository;
import com.eng.homecare.request.ProfessionalRequestDTO;
import com.eng.homecare.response.ProfessionalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProfessionalServices {
    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private UserRepository userRepository;

    public ProfessionalResponseDTO create(ProfessionalRequestDTO professionalRequestDTO){
        Professional professional = ProfessionalMapper.toEntity(professionalRequestDTO);

        userRepository.save(professional.getUser());
        professional = professionalRepository.save(professional);

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
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        return ProfessionalMapper.toDTO(professional);
    }
    public void removeById(Long id){
        Professional professional = professionalRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Profissional não encontrado"));
        professionalRepository.delete(professional);
    }

    public void removeAll(){ professionalRepository.deleteAll();}
}
