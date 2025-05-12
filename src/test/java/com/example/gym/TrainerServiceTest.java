package com.example.gym;


import com.example.gym.dto.TrainerDTO;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.Trainer;
import com.example.gym.repository.TrainerRepository;
import com.example.gym.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void updateTrainer_WithValidData_ShouldUpdateAndReturnTrainerDTO() {
        // Подготовка тестовых данных
        Integer trainerId = 1;
        Trainer existingTrainer = new Trainer();
        existingTrainer.setId(trainerId);
        existingTrainer.setIsActive(true);

        TrainerDTO inputDTO = new TrainerDTO();
        inputDTO.setFullName("Updated Name");
        inputDTO.setSpecialization("Updated Specialization");
        inputDTO.setEmail("updated@example.com");

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(trainerId);
        updatedTrainer.setFullName("Updated Name");
        updatedTrainer.setSpecialization("Updated Specialization");
        updatedTrainer.setEmail("updated@example.com");
        updatedTrainer.setIsActive(true);

        TrainerDTO outputDTO = new TrainerDTO();
        outputDTO.setId(trainerId);
        outputDTO.setFullName("Updated Name");
        outputDTO.setSpecialization("Updated Specialization");
        outputDTO.setEmail("updated@example.com");
        outputDTO.setIsActive(true);

        // Настройка моков
        when(trainerRepository.findByIdAndIsActiveTrue(trainerId)).thenReturn(Optional.of(existingTrainer));
        when(trainerRepository.save(existingTrainer)).thenReturn(updatedTrainer);

        // Используем doAnswer для маппинга DTO -> Entity
        doAnswer(invocation -> {
            TrainerDTO source = invocation.getArgument(0);
            Trainer destination = invocation.getArgument(1);
            if (source != null && destination != null) {
                destination.setFullName(source.getFullName());
                destination.setSpecialization(source.getSpecialization());
                destination.setEmail(source.getEmail());
            }
            return null;
        }).when(modelMapper).map(inputDTO, existingTrainer);

        when(modelMapper.map(updatedTrainer, TrainerDTO.class)).thenReturn(outputDTO);

        // Вызов тестируемого метода
        TrainerDTO result = trainerService.updateTrainer(trainerId, inputDTO);

        // Проверки
        assertNotNull(result);
        assertEquals(outputDTO, result);
        assertEquals("Updated Name", existingTrainer.getFullName());
        assertEquals("Updated Specialization", existingTrainer.getSpecialization());
        assertEquals("updated@example.com", existingTrainer.getEmail());
        verify(trainerRepository, times(1)).save(existingTrainer);
    }

    @Test
    void getAllActiveTrainers_ShouldReturnActiveTrainers() {
        // Подготовка тестовых данных
        Trainer activeTrainer = new Trainer();
        activeTrainer.setIsActive(true);
        TrainerDTO trainerDTO = new TrainerDTO();

        when(trainerRepository.findByIsActiveTrue()).thenReturn(Collections.singletonList(activeTrainer));
        when(modelMapper.map(activeTrainer, TrainerDTO.class)).thenReturn(trainerDTO);

        // Вызов тестируемого метода
        List<TrainerDTO> result = trainerService.getAllActiveTrainers();

        // Проверки
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainerDTO, result.get(0));
        verify(trainerRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    void getTrainerById_WithActiveTrainer_ShouldReturnTrainerDTO() {
        // Подготовка тестовых данных
        Integer trainerId = 1;
        Trainer trainer = new Trainer();
        trainer.setIsActive(true);
        TrainerDTO trainerDTO = new TrainerDTO();

        when(trainerRepository.findByIdAndIsActiveTrue(trainerId)).thenReturn(Optional.of(trainer));
        when(modelMapper.map(trainer, TrainerDTO.class)).thenReturn(trainerDTO);

        // Вызов тестируемого метода
        TrainerDTO result = trainerService.getTrainerById(trainerId);

        // Проверки
        assertNotNull(result);
        assertEquals(trainerDTO, result);
        verify(trainerRepository, times(1)).findByIdAndIsActiveTrue(trainerId);
    }

    @Test
    void createTrainer_ShouldSaveAndReturnTrainerDTO() {
        // Подготовка тестовых данных
        TrainerDTO inputDTO = new TrainerDTO();
        Trainer trainer = new Trainer();
        Trainer savedTrainer = new Trainer();
        TrainerDTO outputDTO = new TrainerDTO();

        when(modelMapper.map(inputDTO, Trainer.class)).thenReturn(trainer);
        when(trainerRepository.save(trainer)).thenReturn(savedTrainer);
        when(modelMapper.map(savedTrainer, TrainerDTO.class)).thenReturn(outputDTO);

        // Вызов тестируемого метода
        TrainerDTO result = trainerService.createTrainer(inputDTO);

        // Проверки
        assertNotNull(result);
        assertEquals(outputDTO, result);
        assertTrue(trainer.getIsActive());
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    void deactivateTrainer_ShouldSetActiveFalse() {
        // Подготовка тестовых данных
        Integer trainerId = 1;
        Trainer trainer = new Trainer();
        trainer.setIsActive(true);

        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));

        // Вызов тестируемого метода
        trainerService.deactivateTrainer(trainerId);

        // Проверки
        assertFalse(trainer.getIsActive());
        verify(trainerRepository, times(1)).save(trainer);
    }
}