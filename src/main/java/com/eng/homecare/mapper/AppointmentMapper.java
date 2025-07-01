package com.eng.homecare.mapper;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.request.AppointmentRequestDTO;
import com.eng.homecare.response.AppointmentRespondeDTO;

public class AppointmentMapper {
    public static Appointment toEntity(AppointmentRequestDTO dto, Professional professional, Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setProfessional(professional);
        appointment.setPatient(patient);
        appointment.setDate(dto.date());
        appointment.setStartTime(dto.startTime());
        appointment.setEndTime(dto.endTime());
        appointment.setStatus(dto.status());
        appointment.setObs(dto.obs());
        return appointment;
    }

    public static AppointmentRespondeDTO toDTO(Appointment appointment) {
        return new AppointmentRespondeDTO(
                appointment.getProfessional().getId(),
                appointment.getPatient().getId(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus(),
                appointment.getObs()
        );
    }
}
