package com.eng.homecare.services;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.Assessment;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.exceptions.ResourceNotFoundException;
import com.eng.homecare.mapper.AppointmentMapper;
import com.eng.homecare.mapper.AssessmentMapper;
import com.eng.homecare.repository.AssessmentRepository;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.request.AssessmentRequestDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final ProfessionalRepository professionalRepository;
    private final PatientRepository patientRepository;
    private final EmailService emailService;

    public AssessmentResponseDTO createAssessment(Long professionalId, Long patientId, AssessmentRequestDTO dto){
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional with ID " + professionalId + " not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + patientId + " not found"));

        Assessment assessment = AssessmentMapper.toEntity(dto, professional, patient);
        assessment.setProfessional(professional);
        assessment.setPatient(patient);
        assessment.setStars(dto.stars());
        assessment.setTitle(dto.title());
        assessment.setDescription(dto.description());

        assessmentRepository.save(assessment);
        updateRate(professional);
        emailService.sendAssessmentEmail(assessment);
        return AssessmentMapper.toDTO(assessment);

    }

    public AssessmentResponseDTO listAssessment(long assessmentId){
        Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow(()->
                new ResourceNotFoundException("Assessment with ID " + assessmentId + " not found"));
        return AssessmentMapper.toDTO(assessment);
    }
    public List<AssessmentResponseDTO> listByProfessionalId(long professionalId){
        if (!professionalRepository.existsById(professionalId)) {
            throw new ResourceNotFoundException("Professional with ID " + professionalId + " not found");
        }
        List<Assessment> assessments = assessmentRepository.findByProfessional_ProfessionalId(professionalId);
        return assessments.stream()
                .map(AssessmentMapper::toDTO)
                .toList();
    }

    @Transactional
    public AssessmentResponseDTO patchAssessment(Long id, AssessmentRequestDTO dto) {
        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment with ID " + id + " not found"));

        if (dto.title() != null) {
            assessment.setTitle(dto.title());
        }
        if (dto.description() != null) {
            assessment.setDescription(dto.description());
        }
        if (dto.stars() != null) {
            assessment.setStars(dto.stars());
            updateRate(assessment.getProfessional());
        }

        return AssessmentMapper.toDTO(assessment);
    }

    public void deleteAssessment(long assessmentId) {

        Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow(()->
                new ResourceNotFoundException("Assessment with ID " + assessmentId + " not found"));

        Professional professional = assessment.getProfessional();

        assessmentRepository.deleteById(assessmentId);
        updateRate(professional);
    }

    public void updateRate(Professional professional){
        List<Assessment> assessments =assessmentRepository.findByProfessional_ProfessionalId(professional.getProfessionalId());
        Float avg = assessments.stream()
                .map(Assessment::getStars)
                .reduce(0f, Float::sum) / assessments.size();
        professional.setRate(avg);
        professionalRepository.save(professional);
    }
}
