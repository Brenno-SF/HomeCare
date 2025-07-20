package com.eng.homecare.services;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.Assessment;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.mapper.AppointmentMapper;
import com.eng.homecare.mapper.AssessmentMapper;
import com.eng.homecare.repository.AssessmentRepository;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.request.AssessmentRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.response.AssessmentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private AssessmentRepository assessmentRepository;
    private ProfessionalRepository professionalRepository;
    private PatientRepository patientRepository;

    public AssessmentResponseDTO createAssessment(Long professionalId, Long patientId, AssessmentRequestDTO dto){
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Assessment assessment = AssessmentMapper.toEntity(dto, professional, patient);
        assessment.setProfessional(professional);
        assessment.setPatient(patient);
        assessment.setStars(dto.stars());
        assessment.setTitle(dto.title());
        assessment.setDescription(dto.description());

        return AssessmentMapper.toDTO(assessment);

    }
    public List<AssessmentResponseDTO> listByProfessionalId(long professionalId){
        List<Assessment> assessments = assessmentRepository.findByProfessional_ProfessionalId(professionalId);
        return assessments.stream()
                .map(AssessmentMapper::toDTO)
                .toList();
    }
}
