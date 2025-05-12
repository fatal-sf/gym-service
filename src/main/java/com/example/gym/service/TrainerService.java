package com.example.gym.service;


import com.example.gym.dto.TrainerDTO;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.model.Trainer;
import com.example.gym.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    public List<TrainerDTO> getAllActiveTrainers() {
        return trainerRepository.findByIsActiveTrue().stream()
                .map(trainer -> modelMapper.map(trainer, TrainerDTO.class))
                .collect(Collectors.toList());
    }

    public TrainerDTO getTrainerById(Integer id) {
        Trainer trainer = trainerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));
        return modelMapper.map(trainer, TrainerDTO.class);
    }

    @Transactional
    public TrainerDTO createTrainer(TrainerDTO trainerDTO) {
        Trainer trainer = modelMapper.map(trainerDTO, Trainer.class);
        trainer.setIsActive(true);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return modelMapper.map(savedTrainer, TrainerDTO.class);
    }

    @Transactional
    public TrainerDTO updateTrainer(Integer id, TrainerDTO trainerDTO) {
        Trainer existingTrainer = trainerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));

        modelMapper.map(trainerDTO, existingTrainer);
        Trainer updatedTrainer = trainerRepository.save(existingTrainer);
        return modelMapper.map(updatedTrainer, TrainerDTO.class);
    }

    @Transactional
    public void deactivateTrainer(Integer id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));
        trainer.setIsActive(false);
        trainerRepository.save(trainer);
    }
}