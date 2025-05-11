package com.example.gym.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TrainingSessionDTO {
    private Long id;

    @NotNull(message = "Trainer ID is required")
    private Long trainerId;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Training type ID is required")
    private Long trainingTypeId;

    @Future(message = "Start time must be in the future")
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @Positive(message = "Duration must be positive")
    @NotNull(message = "Duration is required")
    private Integer durationMinutes;
}