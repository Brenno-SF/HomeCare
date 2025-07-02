package com.eng.homecare.repository;

import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.entities.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityProfessionalRepository extends JpaRepository<AvailabilityProfessional, Long> {
    List<AvailabilityProfessional> findByProfessionalAndWeekDayAndActive(Professional professional, int weekDay, boolean active);
}
