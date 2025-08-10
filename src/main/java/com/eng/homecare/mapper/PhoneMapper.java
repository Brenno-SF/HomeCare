package com.eng.homecare.mapper;

import com.eng.homecare.entities.Phone;
import com.eng.homecare.request.PhoneRequestDTO;
import com.eng.homecare.response.PhoneResponseDTO;

public class PhoneMapper {
    public static Phone toEntity(PhoneRequestDTO dto) {
        Phone phone = new Phone();
        phone.setPhoneNumber(dto.phoneNumber());
        phone.setTypePhone(dto.typePhone());
        return phone;
    }

    public static PhoneResponseDTO toDTO(Phone phone) {
        return new PhoneResponseDTO(
                phone.getId(),
                phone.getPhoneNumber(),
                phone.getTypePhone()
        );
    }
}
