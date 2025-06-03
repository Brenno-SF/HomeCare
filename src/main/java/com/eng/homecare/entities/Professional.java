package com.eng.homecare.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "professional_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professional {

    @Id
    @Column(name = "id_fk")
    private Long professionalId;

    @MapsId
    @JoinColumn(name = "id_fk")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "bio", length = 500, nullable = true)
    private String bio;

    @Column(name = "description", length = 500, nullable = true)
    private String description;

    @Column(name = "rate")
    private int rate;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL)
    private List<Credentials> credentials = new ArrayList<>();
}
