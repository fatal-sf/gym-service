package com.example.gym.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String specialization;

    private String phone;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "trainer")
    private List<TrainingSession> sessions;

    @Column(nullable = false)
    private Boolean isActive = true;
}