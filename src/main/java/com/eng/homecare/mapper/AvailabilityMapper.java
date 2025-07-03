package com.eng.homecare.mapper;

import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.request.AvailabilityProfessionalRequestDTO;
import com.eng.homecare.response.AvailabilityProfessionalResponseDTO;

public class AvailabilityMapper {

    public AvailabilityProfessional toEntity(AvailabilityProfessionalRequestDTO dto) {
        AvailabilityProfessional availability = new AvailabilityProfessional();
        availability.setWeekDay(dto.weekDay());
        availability.setStartTime(dto.startTime());
        availability.setEndTime(dto.endTime());
        availability.setActive(dto.active());
        return availability;
    }

    public static AvailabilityProfessionalResponseDTO toDTO(AvailabilityProfessional availability) {
        return new AvailabilityProfessionalResponseDTO(
                availability.getWeekDay(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getActive()
        );
    }
}
