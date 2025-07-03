package com.eng.homecare.repository;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByProfessional_ProfessionalId(Long professionalId);
    List<Appointment> findByPatient_PatientId(Long patientId);
    List<Appointment> findByProfessional_ProfessionalIdAndDateAndStatus(Long professionalId, LocalDate date, AppointmentStatus appointmentStatus);
}
