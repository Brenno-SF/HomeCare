package com.eng.homecare.entities;

import com.eng.homecare.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @Column(name = "appointment_id", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id_fk", nullable = false)
    private Professional professional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id_fk", nullable = false)
    private Patient patient;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "obs", length = 500)
    private String obs;
}
