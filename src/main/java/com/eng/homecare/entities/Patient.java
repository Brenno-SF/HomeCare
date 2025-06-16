package com.eng.homecare.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "pacient_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient{
    @Id
    @Column(name = "id_fk")
    private Long patientId;

    @MapsId
    @JoinColumn(name = "id_fk")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private List<History> histories = new ArrayList<>();
}
