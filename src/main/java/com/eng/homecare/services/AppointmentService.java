package com.eng.homecare.services;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.enums.AppointmentStatus;
import com.eng.homecare.mapper.AppointmentMapper;
import com.eng.homecare.repository.AppointmentRepository;
import com.eng.homecare.repository.AvailabilityRepository;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.request.AppointmentRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.eng.homecare.enums.AppointmentStatus.CONFIRMED;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;

    public AppointmentResponseDTO createAppointment (AppointmentRequestDTO appointmentRequestDTO){
        Professional professional = professionalRepository.findById(appointmentRequestDTO.professionalId())
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        Patient patient = patientRepository.findById(appointmentRequestDTO.patientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));


        validateWithinAvailability(appointmentRequestDTO,professional);
        validateScheduleConflict(professional.getProfessionalId(), appointmentRequestDTO.date(), appointmentRequestDTO.startTime(), appointmentRequestDTO.endTime());


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

    public AppointmentResponseDTO listById(String id){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Appointment not found"));

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

    public AppointmentResponseDTO updateStatus(String id, AppointmentStatus newStatus){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Appointment not found"));

        appointment.setStatus(newStatus);
        appointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(appointment);

    }

    public void deleteAppointment(String id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    public void validateWithinAvailability(AppointmentRequestDTO appointmentRequestDTO, Professional professional){
        int weekDay = appointmentRequestDTO.date().getDayOfWeek().getValue();

        List<AvailabilityProfessional> availabilities =
                availabilityRepository.findByProfessionalAndWeekDayAndActive(professional, weekDay, true);

        boolean isValid = availabilities.stream().
                anyMatch(availabilityProfessional ->
                                appointmentRequestDTO.startTime().compareTo(availabilityProfessional.getStartTime())>=0 &&
                                appointmentRequestDTO.endTime().compareTo(availabilityProfessional.getEndTime())<=0
                        );

        if (!isValid) {
            throw new IllegalArgumentException("Scheduling outside the professional's availability hours.");
        }
    }

    public void validateScheduleConflict(long professionalId, LocalDate date, LocalTime start, LocalTime end){
        List<Appointment> existingAppointments = appointmentRepository.findByProfessional_ProfessionalIdAndDateAndStatus(professionalId,date, CONFIRMED);

        boolean overlaps = existingAppointments.stream().anyMatch(existing->
                start.isBefore(existing.getEndTime())&& end.isAfter(existing.getStartTime())
        );

        if (overlaps) {
            throw new IllegalArgumentException("There is already an appointment scheduled at that time for this professional.");
        }
    }

}

