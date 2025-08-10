package com.eng.homecare.services;

import com.eng.homecare.entities.Phone;
import com.eng.homecare.entities.User;
import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.exceptions.ResourceNotFoundException;
import com.eng.homecare.mapper.PhoneMapper;
import com.eng.homecare.repository.PhoneRepository;
import com.eng.homecare.repository.UserRepository;
import com.eng.homecare.request.PhoneRequestDTO;
import com.eng.homecare.response.PhoneResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;

    public PhoneResponseDTO create(PhoneRequestDTO phoneRequestDTO, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User with ID " + userId + " not found"));

        Phone phone = PhoneMapper.toEntity(phoneRequestDTO);

        phone.setUser(user);
        phoneRepository.save(phone);
        return PhoneMapper.toDTO(phone);
    }
    public PhoneResponseDTO update(Long phoneId, PhoneRequestDTO phoneRequestDTO, Long userId){
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(()->
                new ResourceNotFoundException("phone with ID " + phoneId + " not found"));

        if(!phone.getUser().getUserId().equals(userId)){
            throw new ForbiddenAccessException("You cannot update another user's phone");
        }

        phone.setTypePhone(phoneRequestDTO.typePhone());
        phone.setPhoneNumber(phoneRequestDTO.phoneNumber());

        return PhoneMapper.toDTO(phone);

    }
}
