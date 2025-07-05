package com.eng.homecare.response;

import com.eng.homecare.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(ProfessionalResumeDTO professional,
                                     long patientId,
                                     LocalDate date,
                                     LocalTime startTime,
                                     LocalTime endTime,
                                     AppointmentStatus status,
                                     String obs) {
}
