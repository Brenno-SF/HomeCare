package com.eng.homecare.request;


import com.eng.homecare.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(LocalDate date,
                                    LocalTime startTime,
                                    LocalTime endTime,
                                    String obs) {
}
