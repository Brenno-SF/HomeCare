package com.eng.homecare.repository;

import com.eng.homecare.entities.History;
import com.eng.homecare.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByPatient(Patient patient);
}
