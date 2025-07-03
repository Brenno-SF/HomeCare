package com.eng.homecare.services;

import com.eng.homecare.entities.AvailabilityProfessional;
import com.eng.homecare.entities.Professional;
import com.eng.homecare.mapper.AvailabilityMapper;
import com.eng.homecare.repository.AvailabilityRepository;
import com.eng.homecare.repository.ProfessionalRepository;
import com.eng.homecare.request.AvailabilityRequestDTO;
import com.eng.homecare.response.AvailabilityResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository repository;
    @Autowired
    private ProfessionalRepository professionalRepository;

    public List<AvailabilityResponseDTO> createAvailability (Long professionalId){
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

    public AvailabilityResponseDTO updateAvailability(AvailabilityRequestDTO availabilityRequestDTO, Long professionalId){
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found"));

        AvailabilityProfessional availabilitySaved = repository.findByProfessionalAndWeekDay(professional,availabilityRequestDTO.weekDay());

        availabilitySaved.setActive(availabilityRequestDTO.active());
        availabilitySaved.setStartTime(availabilityRequestDTO.startTime());
        availabilitySaved.setEndTime(availabilityRequestDTO.endTime());

        return AvailabilityMapper.toDTO(repository.save(availabilitySaved));

    }
}
