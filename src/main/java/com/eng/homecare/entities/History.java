package com.eng.homecare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "history_id")
    private Long historyId;
    @ManyToOne
    @JoinColumn(name = "user_id_fk", nullable = false)
    @JsonIgnore
    private User user;
    private String history;

}
