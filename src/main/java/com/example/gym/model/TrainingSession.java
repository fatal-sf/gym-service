package com.example.gym.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor (access = AccessLevel.PUBLIC)
@Entity
@Table(name = "training_sessions")
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType trainingType;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Boolean isCancelled = false;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}