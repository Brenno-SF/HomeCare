package com.eng.homecare.repository;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByProfessional_ProfessionalId(Long professionalId);
}
