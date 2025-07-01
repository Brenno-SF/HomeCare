package com.eng.homecare.services;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.enums.AppointmentStatus;
import com.eng.homecare.mapper.AppointmentMapper;
import com.eng.homecare.repository.AppointmentRepository;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.request.AppointmentRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private PatientRepository patientRepository;


    public AppointmentResponseDTO createAppointment (AppointmentRequestDTO appointmentRequestDTO){
        Professional professional = professionalRepository.findById(appointmentRequestDTO.professionalId())
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        Patient patient = patientRepository.findById(appointmentRequestDTO.patientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Appointment appointment = AppointmentMapper.toEntity(appointmentRequestDTO, professional, patient);

        appointment.setProfessional(professional);
        appointment.setPatient(patient);
        appointment.setDate(appointmentRequestDTO.date());
        appointment.setStartTime(appointmentRequestDTO.startTime());
        appointment.setEndTime(appointmentRequestDTO.endTime());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setObs(appointmentRequestDTO.obs());

        appointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(appointment);
    }

    public List<AppointmentResponseDTO> listByProfessionalId(long professionalId){
        List<Appointment> appointments = appointmentRepository.findByProfessional_ProfessionalId(professionalId);
        return appointments.stream()
                .map(AppointmentMapper::toDTO)
                .toList();
    }

    public List<AppointmentResponseDTO> listByPatientId(long patientId){
        List<Appointment> appointments = appointmentRepository.findByPatient_PatientId(patientId);
        return appointments.stream()
                .map(AppointmentMapper::toDTO)
                .toList();
    }




}
