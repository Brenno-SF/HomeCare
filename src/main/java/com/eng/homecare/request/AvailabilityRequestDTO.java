package com.eng.homecare.request;

import java.time.LocalTime;

public record AvailabilityRequestDTO(
                                                 Integer weekDay,
                                                 LocalTime startTime,
                                                 LocalTime endTime,
                                                 Boolean active) {
}
