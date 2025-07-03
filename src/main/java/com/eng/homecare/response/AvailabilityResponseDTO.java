package com.eng.homecare.response;

import java.time.LocalTime;

public record AvailabilityResponseDTO(Integer weekDay,
                                      LocalTime startTime,
                                      LocalTime endTime,
                                      Boolean active) {
}
