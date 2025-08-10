package com.eng.homecare.services;

import com.eng.homecare.entities.History;
import com.eng.homecare.entities.Patient;
import com.eng.homecare.exceptions.ForbiddenAccessException;
import com.eng.homecare.exceptions.ResourceNotFoundException;
import com.eng.homecare.mapper.HistoryMapper;
import com.eng.homecare.repository.HistoryRepository;
import com.eng.homecare.repository.PatientRepository;
import com.eng.homecare.request.HistoryRequestDTO;
import com.eng.homecare.response.HistoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final PatientRepository patientRepository;

    public HistoryResponseDTO create(HistoryRequestDTO historyRequestDTO, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + patientId + " not found"));

        History history = HistoryMapper.toEntity(historyRequestDTO);
        history.setPatient(patient);

        historyRepository.save(history);

        return HistoryMapper.toDTO(history);
    }
    public HistoryResponseDTO update(Long historyId, HistoryRequestDTO historyRequestDTO, Long patientId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new ResourceNotFoundException("History with ID " + historyId + " not found"));

        if (!history.getPatient().getPatientId().equals(patientId)) {
            throw new ForbiddenAccessException("You cannot update another patient's history");
        }

        history.setHistory(historyRequestDTO.history());
        historyRepository.save(history);

        return HistoryMapper.toDTO(history);
    }


}
