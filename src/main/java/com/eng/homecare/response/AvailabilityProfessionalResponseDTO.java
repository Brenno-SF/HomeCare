package com.eng.homecare.response;

import com.eng.homecare.entities.Professional;

import java.time.LocalTime;

public record AvailabilityProfessionalResponseDTO(Integer weekDay,
                                                  LocalTime startTime,
                                                  LocalTime endTime,
                                                  Boolean active) {
}
