package com.eng.homecare.repository;

import com.eng.homecare.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByProfessional_ProfessionalId(Long professionalId);
    List<Appointment> findByPatient_PatientId(Long patientId);
    List<Appointment> findByProfessional_ProfessionalIdAndDate(Long professionalId, LocalDate date);
}
