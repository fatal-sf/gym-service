package com.example.gym.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TrainingSessionDTO {
    private Long id;

    @NotNull(message = "Требуется идентификатор тренера")
    private Long trainerId;

    @NotNull(message = "Требуется идентификатор клиента")
    private Integer clientId;

    @NotNull(message = "Требуется идентификатор тренировки")
    private Long trainingTypeId;

    @Future(message = "Время начала тренировки должно быть в будущем")
    @NotNull(message = "Требуется время тренировки")
    private LocalDateTime startTime;

    @Positive(message = "Длительность должна быть положительной")
    @NotNull(message = "Требуется продолжительность")
    private Integer durationMinutes;
}