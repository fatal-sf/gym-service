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
    void getTrainerById_WhenExists_ReturnsTrainerDTO() {
        Long trainerId = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        trainer.setIsActive(true);

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setId(trainerId);

        when(trainerRepository.findByIdAndIsActiveTrue(trainerId)).thenReturn(Optional.of(trainer));
        when(modelMapper.map(trainer, TrainerDTO.class)).thenReturn(trainerDTO);

        TrainerDTO result = trainerService.getTrainerById(trainerId);

        assertNotNull(result);
        assertEquals(trainerId, result.getId());
    }

    @Test
    void getTrainerById_WhenNotExists_ThrowsException() {
        Long trainerId = 1L;
        when(trainerRepository.findByIdAndIsActiveTrue(trainerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> trainerService.getTrainerById(trainerId));
    }
}