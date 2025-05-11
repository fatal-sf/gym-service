package com.example.gym.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    private LocalDate birthDate;
    private String phone;
    private String email;

    @Column(unique = true)
    private String membershipNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<TrainingSession> sessions;

    @Column(nullable = false)
    private Boolean isActive = true;
}