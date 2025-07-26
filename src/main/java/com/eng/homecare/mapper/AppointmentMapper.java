package com.eng.homecare.mapper;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.enums.TypeUser;
import com.eng.homecare.request.AppointmentRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.response.PatientResumeDTO;
import com.eng.homecare.response.ProfessionalResumeDTO;

public class AppointmentMapper {
    public static Appointment toEntity(AppointmentRequestDTO dto, Professional professional, Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setDate(dto.date());
        appointment.setStartTime(dto.startTime());
        appointment.setEndTime(dto.endTime());
        appointment.setObs(dto.obs());
        return appointment;
    }

    public static AppointmentResponseDTO toDTO(Appointment appointment) {
        ProfessionalResumeDTO professional = new ProfessionalResumeDTO(appointment.getProfessional().getProfessionalId(),appointment.getProfessional().getUser().getName(), appointment.getProfessional().getBio());
        PatientResumeDTO patient = new PatientResumeDTO(appointment.getPatient().getPatientId(), appointment.getPatient().getUser().getName(), appointment.getPatient().getHealthInsurance(), appointment.getPatient().getUser().getGender());
        return new AppointmentResponseDTO(
                appointment.getAppointmentId(),
                professional,
                patient,
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus(),
                appointment.getObs()
        );
    }
}
