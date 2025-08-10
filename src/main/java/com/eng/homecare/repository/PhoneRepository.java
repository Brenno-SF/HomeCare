package com.eng.homecare.repository;

import com.eng.homecare.entities.Phone;
import com.eng.homecare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByUser(User user);
}
