package com.eng.homecare.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "availability_professional_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityProfessional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Integer availabilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id_fk", nullable = false)
    private Professional professional;

    @Column(name = "week_day")
    private Integer weekDay; // 1 = Domingo, 2 = Segunda, etc.

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "active")
    private Boolean active;
}

