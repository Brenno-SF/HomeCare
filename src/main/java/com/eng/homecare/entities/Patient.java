package com.eng.homecare.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "pacient_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient{
    @Id
    @Column(name = "id_fk")
    private Integer patientId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_fk")
    private User user;

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;
}
