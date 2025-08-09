package com.eng.homecare.repository;

import com.eng.homecare.entities.Address;
import com.eng.homecare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByIdAndUser(Long id, User user);
}
