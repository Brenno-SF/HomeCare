package com.eng.homecare.request;

import com.eng.homecare.entities.Professional;

import java.time.LocalTime;

public record AvailabilityProfessionalRequestDTO(Professional professional,
                                                 Integer weekDay,
                                                 LocalTime startTime,
                                                 LocalTime endTime,
                                                 Boolean active) {
}
