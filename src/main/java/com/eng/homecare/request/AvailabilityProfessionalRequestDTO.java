package com.eng.homecare.request;

import com.eng.homecare.entities.Professional;

import java.time.LocalTime;

public record AvailabilityProfessionalRequestDTO(
                                                 Integer weekDay,
                                                 LocalTime startTime,
                                                 LocalTime endTime,
                                                 Boolean active) {
}
