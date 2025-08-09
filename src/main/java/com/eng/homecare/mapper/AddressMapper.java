package com.eng.homecare.mapper;

import com.eng.homecare.entities.Address;
import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.request.AddressRequestDTO;
import com.eng.homecare.request.AvailabilityRequestDTO;
import com.eng.homecare.response.AddressResponseDTO;
import com.eng.homecare.response.AvailabilityResponseDTO;

public class AddressMapper {

    public Address toEntity(AddressRequestDTO dto) {
        Address address = new Address();
        address.setCity(dto.city());
        address.setFu(dto.fu());
        address.setComplement(dto.complement());
        address.setNumber(dto.number());
        address.setNeighborhood(dto.neighborhood());
        address.setZipCode(dto.zipCode());
        address.setStreet(dto.street());
        return address;
    }

    public static AddressResponseDTO toDTO(Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getComplement(),
                address.getCity(),
                address.getZipCode(),
                address.getFu()
        );
    }
}
