package com.eng.homecare.services;

import com.eng.homecare.entities.Credentials;
import com.eng.homecare.exceptions.ResourceNotFoundException;
import com.eng.homecare.mapper.CredentialsMapper;
import com.eng.homecare.repository.CredentialsRepository;
import com.eng.homecare.request.CredentialsRequestDTO;
import com.eng.homecare.response.CredentialsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialsService {
    private final CredentialsRepository credentialsRepository;

    public CredentialsResponseDTO update(Long credentialId, CredentialsRequestDTO credentialsRequestDTO, Long professionalId){
        Credentials credentials = credentialsRepository.findById(credentialId).orElseThrow(()->
                new ResourceNotFoundException("Credential with ID" + credentialId + "not found"));

        if (!credentials.getProfessional().getProfessionalId().equals(professionalId))
            throw new ResourceNotFoundException("You cannot update another professional's credential");

        credentials.setFu(credentialsRequestDTO.fu());
        credentials.setNumber(credentialsRequestDTO.number());
        credentials.setType(credentialsRequestDTO.type());
        credentials.setValidationDate(credentialsRequestDTO.validation_date());

        credentialsRepository.save(credentials);

        return CredentialsMapper.toDTO(credentials);
    }

}
