package com.eng.homecare.repository;

import com.eng.homecare.entities.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
}
