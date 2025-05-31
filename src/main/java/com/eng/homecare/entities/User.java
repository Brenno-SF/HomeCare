package com.eng.homecare.entities;

import com.eng.homecare.enums.TypeUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name="user_tb")
@Table(name="user_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) @Column(name = "user_id")
    private String userId;
    private String name;
    private String email;
    private String password;
    @Column(name = "type_user")
    private TypeUser typeUser;
    @Column(name="birth_date")
    private LocalDate birthDate;
    private String gender;
    private LocalDateTime register;
}
