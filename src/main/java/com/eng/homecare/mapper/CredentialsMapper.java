package com.eng.homecare.mapper;

import com.eng.homecare.entities.Credentials;
import com.eng.homecare.request.CredentialsRequestDTO;
import com.eng.homecare.response.CredentialsResponseDTO;

public class CredentialsMapper {

    public static Credentials toEntity(CredentialsRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Credentials credentials = new Credentials();
        credentials.setProfessional(dto.professional());
        credentials.setNumber(dto.number());
        credentials.setFu(dto.fu());
        credentials.setValidationDate(dto.validation_date());
        credentials.setType(dto.type());
        return credentials;
    }

    public static CredentialsResponseDTO toDTO(Credentials credentials) {
        if (credentials == null) {
            return null;
        }

        return new CredentialsResponseDTO(
                credentials.getCredentialId(),
                credentials.getNumber(),
                credentials.getFu(),
                credentials.getValidationDate(),
                credentials.getType()
        );
    }
}
