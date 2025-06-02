package com.eng.homecare.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.UniqueKey;

import java.time.LocalDateTime;

@Entity(name = "credential_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    @Id
    @Column(name = "credentials_id")
    private Long credentialId;

    @MapsId
    @JoinColumn(name = "professional_id_fk")
    @OneToOne(cascade =  CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Professional professional;

    @Column(name = "number", length = 20,nullable = false, unique = true)
    String number;

    @Column(name = "fu", length = 2, nullable = false)
    String fu;

    @Column(name = "validation_date", nullable = false)
    LocalDateTime validationDate;

    @Column(name = "type", length = 30, nullable = false)
    String type;

}
