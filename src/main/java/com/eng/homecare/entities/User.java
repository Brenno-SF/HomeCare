package com.eng.homecare.entities;

import com.eng.homecare.enums.TypeUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="user_tb")
@Table(name="user_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    private Long userId;
    private String name;
    private String email;
    private String password;
    @Column(name = "type_user")
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    @Column(name="birth_date")
    private LocalDate birthDate;
    private String gender;
    @CreationTimestamp
    private LocalDateTime register;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();
}
