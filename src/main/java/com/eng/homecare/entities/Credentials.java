package com.eng.homecare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "credentials_tb")
@Table(name = "credentials_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credentials_id")
    private Long credentialId;

    @ManyToOne
    @JoinColumn(name = "professional_id_fk", nullable = false)
    @JsonIgnore
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
