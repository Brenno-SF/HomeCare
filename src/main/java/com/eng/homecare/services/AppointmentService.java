package com.eng.homecare.services;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.enums.AppointmentStatus;
import com.eng.homecare.exceptions.ForbiddenAccessException;
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
    @Autowired
    private EmailService emailService;

    public AppointmentResponseDTO createAppointment (Long professionalId, Long patientId,AppointmentRequestDTO appointmentRequestDTO){
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        Patient patient = patientRepository.findById(patientId)
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


        // Enviar e-mail para o profissional notificando sobre a nova consulta
        String emailBody = """
            Olá Dr(a). %s,
        
                Você recebeu uma nova solicitação de consulta através da plataforma HomeCare.
            
                🧑 Paciente: %s
                📅 Data: %s
                🕒 Horário: das %s às %s
                📝 Observações: %s
            
                Para mais detalhes ou para confirmar o agendamento, acesse seu painel na plataforma.
        
            Atenciosamente,
            Equipe HomeCare
        """.formatted(
                        professional.getUser().getName(),
                        patient.getUser().getName(),
                        appointment.getDate().toString(),
                        appointment.getStartTime().toString(),
                        appointment.getEndTime().toString(),
                        appointment.getObs() == null || appointment.getObs().isBlank() ? "Nenhuma observação." : appointment.getObs()
                );

        // Envia o e-mail
        emailService.sendSimpleEmail(
                professional.getUser().getEmail(),
                "Nova consulta agendada por paciente",
                emailBody
        );

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

    public AppointmentResponseDTO confirmAppointment(String id, Long professionalId) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        if (!appointment.getProfessional().getProfessionalId().equals(professionalId)) {
            throw new ForbiddenAccessException();
        }

        appointment.setStatus(CONFIRMED);
        appointment = appointmentRepository.save(appointment);

        emailService.sendConfirmAppointment(appointment);

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

