package com.example.gym.controller;


import com.example.gym.dto.TrainingSessionDTO;
import com.example.gym.service.TrainingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
@Tag(name = "Training Session Management", description = "APIs for managing training sessions")
public class TrainingSessionController {
    private final TrainingSessionService sessionService;

    @GetMapping
    @Operation(summary = "Get all active training sessions")
    public ResponseEntity<List<TrainingSessionDTO>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getAllActiveSessions());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get training session by ID")
    public ResponseEntity<TrainingSessionDTO> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new training session")
    public ResponseEntity<TrainingSessionDTO> createSession(
            @Valid @RequestBody TrainingSessionDTO sessionDTO) {
        return new ResponseEntity<>(sessionService.createSession(sessionDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel training session")
    public ResponseEntity<Void> cancelSession(@PathVariable Long id) {
        sessionService.cancelSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get client's training sessions")
    public ResponseEntity<List<TrainingSessionDTO>> getClientSessions(@PathVariable Long clientId) {
        return ResponseEntity.ok(sessionService.getClientSessions(clientId));
    }

    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Get trainer's training sessions")
    public ResponseEntity<List<TrainingSessionDTO>> getTrainerSessions(@PathVariable Long trainerId) {
        return ResponseEntity.ok(sessionService.getTrainerSessions(trainerId));
    }
}
