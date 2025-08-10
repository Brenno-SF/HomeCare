package com.eng.homecare.repository;

import com.eng.homecare.entities.Address;
import com.eng.homecare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
