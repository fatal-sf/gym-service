package com.example.gym;

import com.example.gym.dto.TrainingSessionDTO;
import com.example.gym.exception.BusinessException;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.*;
import com.example.gym.repository.*;
import com.example.gym.service.TrainingSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingSessionServiceTest {

    @Mock
    private TrainingSessionRepository sessionRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TrainingTypeRepository typeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TrainingSessionService sessionService;

    @Test
    void getAllActiveSessions_ShouldReturnActiveSessions() {
        // Подготовка тестовых данных
        TrainingSession session = new TrainingSession();
        TrainingSessionDTO sessionDTO = new TrainingSessionDTO();

        when(sessionRepository.findAllActive()).thenReturn(Collections.singletonList(session));
        when(modelMapper.map(session, TrainingSessionDTO.class)).thenReturn(sessionDTO);

        // Вызов тестируемого метода
        List<TrainingSessionDTO> result = sessionService.getAllActiveSessions();

        // Проверки
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sessionDTO, result.get(0));
        verify(sessionRepository, times(1)).findAllActive();
        verify(modelMapper, times(1)).map(session, TrainingSessionDTO.class);
    }

    @Test
    void getSessionById_WithValidId_ShouldReturnSessionDTO() {
        // Подготовка тестовых данных
        Integer sessionId = 1;
        TrainingSession session = new TrainingSession();
        TrainingSessionDTO sessionDTO = new TrainingSessionDTO();

        when(sessionRepository.findByIdAndIsCancelledFalse(sessionId)).thenReturn(Optional.of(session));
        when(modelMapper.map(session, TrainingSessionDTO.class)).thenReturn(sessionDTO);

        // Вызов тестируемого метода
        TrainingSessionDTO result = sessionService.getSessionById(sessionId);

        // Проверки
        assertNotNull(result);
        assertEquals(sessionDTO, result);
        verify(sessionRepository, times(1)).findByIdAndIsCancelledFalse(sessionId);
        verify(modelMapper, times(1)).map(session, TrainingSessionDTO.class);
    }

    @Test
    void createSession_WithAvailableTrainer_ShouldCreateSession() {
        // Подготовка тестовых данных
        TrainingSessionDTO inputDTO = new TrainingSessionDTO();
        inputDTO.setStartTime(LocalDateTime.now());
        inputDTO.setDurationMinutes(60);
        inputDTO.setTrainerId(1);
        inputDTO.setClientId(1);
        inputDTO.setTrainingTypeId(1);

        TrainingSession session = new TrainingSession();
        Trainer trainer = new Trainer();
        Client client = new Client();
        TrainingType type = new TrainingType();
        TrainingSessionDTO outputDTO = new TrainingSessionDTO();

        when(sessionRepository.existsByTrainerAndTimeRange(anyInt(), any(), any())).thenReturn(false);
        when(trainerRepository.findById(anyInt())).thenReturn(Optional.of(trainer));
        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        when(typeRepository.findById(anyInt())).thenReturn(Optional.of(type));
        when(modelMapper.map(inputDTO, TrainingSession.class)).thenReturn(session);
        when(sessionRepository.save(session)).thenReturn(session);
        when(modelMapper.map(session, TrainingSessionDTO.class)).thenReturn(outputDTO);

        // Вызов тестируемого метода
        TrainingSessionDTO result = sessionService.createSession(inputDTO);

        // Проверки
        assertNotNull(result);
        assertEquals(outputDTO, result);
        verify(sessionRepository, times(1)).save(session);
        verify(modelMapper, times(1)).map(session, TrainingSessionDTO.class);
    }

    @Test
    void createSession_WithBusyTrainer_ShouldThrowException() {
        // Подготовка тестовых данных
        TrainingSessionDTO inputDTO = new TrainingSessionDTO();
        inputDTO.setStartTime(LocalDateTime.now());
        inputDTO.setDurationMinutes(60);
        inputDTO.setTrainerId(1);

        when(sessionRepository.existsByTrainerAndTimeRange(anyInt(), any(), any())).thenReturn(true);

        // Проверка исключения
        assertThrows(BusinessException.class, () -> {
            sessionService.createSession(inputDTO);
        });
    }

    @Test
    void cancelSession_ShouldSetCancelledTrue() {
        // Подготовка тестовых данных
        Integer sessionId = 1;
        TrainingSession session = new TrainingSession();

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Вызов тестируемого метода
        sessionService.cancelSession(sessionId);

        // Проверки
        assertTrue(session.getIsCancelled());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void getClientSessions_ShouldReturnActiveSessions() {
        // Подготовка тестовых данных
        Integer clientId = 1;
        TrainingSession session = new TrainingSession();
        session.setIsCancelled(false);
        TrainingSessionDTO sessionDTO = new TrainingSessionDTO();

        when(sessionRepository.findByClientId(clientId)).thenReturn(Collections.singletonList(session));
        when(modelMapper.map(session, TrainingSessionDTO.class)).thenReturn(sessionDTO);

        // Вызов тестируемого метода
        List<TrainingSessionDTO> result = sessionService.getClientSessions(clientId);

        // Проверки
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sessionDTO, result.get(0));
        verify(sessionRepository, times(1)).findByClientId(clientId);
        verify(modelMapper, times(1)).map(session, TrainingSessionDTO.class);
    }

    @Test
    void getTrainerSessions_ShouldReturnActiveSessions() {
        // Подготовка тестовых данных
        Integer trainerId = 1;
        TrainingSession session = new TrainingSession();
        session.setIsCancelled(false);
        TrainingSessionDTO sessionDTO = new TrainingSessionDTO();

        when(sessionRepository.findByTrainerId(trainerId)).thenReturn(Collections.singletonList(session));
        when(modelMapper.map(session, TrainingSessionDTO.class)).thenReturn(sessionDTO);

        // Вызов тестируемого метода
        List<TrainingSessionDTO> result = sessionService.getTrainerSessions(trainerId);

        // Проверки
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sessionDTO, result.get(0));
        verify(sessionRepository, times(1)).findByTrainerId(trainerId);
        verify(modelMapper, times(1)).map(session, TrainingSessionDTO.class);
    }
}