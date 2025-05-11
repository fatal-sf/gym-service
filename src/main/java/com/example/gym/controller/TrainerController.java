package com.example.gym.controller;


import com.example.gym.dto.TrainerDTO;
import com.example.gym.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
@Tag(name = "Управление тренерами", description = "API для управления тренерами тренажерного зала")
public class TrainerController {
    private final TrainerService trainerService;


    @GetMapping
    @Operation(summary = "Получить всех активных тренеров")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllActiveTrainers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тренера по ID")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @PostMapping
    @Operation(summary = "Создать нового тренера")
    public ResponseEntity<TrainerDTO> createTrainer(@Valid @RequestBody TrainerDTO trainerDTO) {
        return new ResponseEntity<>(trainerService.createTrainer(trainerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о тренере")
    public ResponseEntity<TrainerDTO> updateTrainer(
            @PathVariable Long id,
            @Valid @RequestBody TrainerDTO trainerDTO) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainerDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Деактивировать тренера")
    public ResponseEntity<Void> deactivateTrainer(@PathVariable Long id) {
        trainerService.deactivateTrainer(id);
        return ResponseEntity.noContent().build();
    }
}