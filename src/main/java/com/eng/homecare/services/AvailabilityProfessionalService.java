package com.eng.homecare.services;

import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.mapper.AvailabilityMapper;
import com.eng.homecare.mapper.ProfessionalMapper;
import com.eng.homecare.repository.AvailabilityProfessionalRepository;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.request.AvailabilityProfessionalRequestDTO;
import com.eng.homecare.response.AvailabilityProfessionalResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityProfessionalService {
    @Autowired
    private AvailabilityProfessionalRepository repository;
    @Autowired
    private ProfessionalRepository professionalRepository;

    public List<AvailabilityProfessionalResponseDTO> createAvailability (Long professionalId){
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        List<AvailabilityProfessional> availabilityList = new ArrayList<>();

        for (DayOfWeek day: DayOfWeek.values()){
            AvailabilityProfessional availabilityProfessional = new AvailabilityProfessional();
            availabilityProfessional.setProfessional(professional);
            availabilityProfessional.setActive(false);
            availabilityProfessional.setStartTime(LocalTime.MIN);
            availabilityProfessional.setEndTime(LocalTime.MIN);
            availabilityProfessional.setWeekDay(day.getValue());

            availabilityList.add(availabilityProfessional);
        }
        List<AvailabilityProfessional> savedList = repository.saveAll(availabilityList);

        return savedList.stream().
                map(AvailabilityMapper::toDTO).
                toList();
    }
}
