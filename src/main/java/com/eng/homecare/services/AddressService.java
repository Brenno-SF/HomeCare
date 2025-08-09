package com.eng.homecare.services;

import com.eng.homecare.entities.Address;
import com.eng.homecare.entities.User;
import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.exceptions.ResourceNotFoundException;
import com.eng.homecare.mapper.AddressMapper;
import com.eng.homecare.repository.AddressRepository;
import com.eng.homecare.repository.UserRepository;
import com.eng.homecare.request.AddressRequestDTO;
import com.eng.homecare.response.AddressResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public AddressResponseDTO update(Long addressId, AddressRequestDTO addressRequestDTO, Long userId){

        Address address = addressRepository.findById(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address with ID " + addressId + " not found"));

        if(!address.getUser().getUserId().equals(userId)){
            throw new ForbiddenAccessException("You cannot update another user's address");
        }

        address.setStreet(addressRequestDTO.street());
        address.setNumber(addressRequestDTO.number());
        address.setNeighborhood(addressRequestDTO.neighborhood());
        address.setComplement(addressRequestDTO.complement());
        address.setCity(addressRequestDTO.city());
        address.setFu(addressRequestDTO.fu());
        address.setZipCode(addressRequestDTO.zipCode());

        addressRepository.save(address);
        return AddressMapper.toDTO(address);
    }


    }
}
