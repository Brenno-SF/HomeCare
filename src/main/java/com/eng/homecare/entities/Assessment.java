package com.eng.homecare.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "assessment")
@Data
public class Assessment {
 /*
     professional_id_fk int(11) PK
    pacient_id_fk int(11) PK
    title varchar(100)
    description varchar(500)
    stars float
  */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id_fk", nullable = false)
    private Professional professional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacient_id_fk", nullable = false)
    private Patient patient;
    @Column(length = 100)
    private String title;
    @Column(length = 500)
    private String description;
    private Float stars;

}
