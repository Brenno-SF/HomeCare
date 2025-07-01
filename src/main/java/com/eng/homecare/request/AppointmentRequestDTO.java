package com.eng.homecare.request;


import com.eng.homecare.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(long professionalId,
                                    long patientId,
                                    LocalDate date,
                                    LocalTime startTime,
                                    LocalTime endTime,
                                    AppointmentStatus status,
                                    String obs) {
}
