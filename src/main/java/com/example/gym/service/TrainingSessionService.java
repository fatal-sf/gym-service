package com.example.gym.service;

import com.example.gym.dto.TrainingSessionDTO;
import com.example.gym.exception.BusinessException;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.*;
import com.example.gym.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingSessionService {
    private final TrainingSessionRepository sessionRepository;
    private final TrainerRepository trainerRepository;
    private final ClientRepository clientRepository;
    private final TrainingTypeRepository typeRepository;
    private final ModelMapper modelMapper;

    public List<TrainingSessionDTO> getAllActiveSessions() {
        return sessionRepository.findAllActive().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TrainingSessionDTO getSessionById(Integer id) {
        TrainingSession session = sessionRepository.findByIdAndIsCancelledFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        return convertToDto(session);
    }

    @Transactional
    public TrainingSessionDTO createSession(TrainingSessionDTO sessionDTO) {
        LocalDateTime endTime = sessionDTO.getStartTime().plusMinutes(sessionDTO.getDurationMinutes());

        boolean isTrainerAvailable = !sessionRepository.existsByTrainerAndTimeRange(
                sessionDTO.getTrainerId(),
                sessionDTO.getStartTime(),
                endTime);

        if (!isTrainerAvailable) {
            throw new BusinessException("Trainer is not available at this time");
        }

        TrainingSession session = modelMapper.map(sessionDTO, TrainingSession.class);
        session.setId(null);
        session.setTrainer(trainerRepository.findById(sessionDTO.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found")));
        session.setClient(clientRepository.findById(sessionDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found")));
        session.setTrainingType(typeRepository.findById(sessionDTO.getTrainingTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Training type not found")));

        return convertToDto(sessionRepository.save(session));
    }

    private TrainingSessionDTO convertToDto(TrainingSession session) {
        return modelMapper.map(session, TrainingSessionDTO.class);
    }
    @Transactional
    public void cancelSession(Integer sessionId) {
        TrainingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        session.setIsCancelled(true);
        sessionRepository.save(session);
    }

    public List<TrainingSessionDTO> getClientSessions(Integer clientId) {
        return sessionRepository.findByClientId(clientId).stream()
                .filter(session -> !session.getIsCancelled())
                .map(this::convertToDto)
                .toList();
    }

    public List<TrainingSessionDTO> getTrainerSessions(Integer trainerId) {
        return sessionRepository.findByTrainerId(trainerId).stream()
                .filter(session -> !session.getIsCancelled())
                .map(this::convertToDto)
                .toList();
    }
}