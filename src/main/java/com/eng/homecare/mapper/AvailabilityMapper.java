package com.eng.homecare.mapper;

import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.request.AvailabilityRequestDTO;
import com.eng.homecare.response.AvailabilityResponseDTO;

public class AvailabilityMapper {

    public AvailabilityProfessional toEntity(AvailabilityRequestDTO dto) {
        AvailabilityProfessional availability = new AvailabilityProfessional();
        availability.setWeekDay(dto.weekDay());
        availability.setStartTime(dto.startTime());
        availability.setEndTime(dto.endTime());
        availability.setActive(dto.active());
        return availability;
    }

    public static AvailabilityResponseDTO toDTO(AvailabilityProfessional availability) {
        return new AvailabilityResponseDTO(
                availability.getWeekDay(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getActive()
        );
    }
}
