package com.eng.homecare.repository;

import com.eng.homecare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
