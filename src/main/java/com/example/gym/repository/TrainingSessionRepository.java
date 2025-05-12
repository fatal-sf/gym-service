package com.example.gym.repository;

import com.example.gym.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer> {

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.id = :id AND ts.isCancelled = false")
    Optional<TrainingSession> findByIdAndIsCancelledFalse(@Param("id") Integer id);

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.isCancelled = false")
    List<TrainingSession> findAllActive();

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.client.id = :clientId AND ts.isCancelled = false")
    List<TrainingSession> findByClientIdAndActive(@Param("clientId") Integer clientId);

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.trainer.id = :trainerId AND ts.isCancelled = false")
    List<TrainingSession> findByTrainerIdAndActive(@Param("trainerId") Integer trainerId);

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.trainingType.id = :typeId AND ts.isCancelled = false")
    List<TrainingSession> findByTrainingTypeIdAndActive(@Param("typeId") Integer typeId);

    @Query("SELECT CASE WHEN COUNT(ts) > 0 THEN true ELSE false END " +
            "FROM TrainingSession ts " +
            "WHERE ts.trainer.id = :trainerId " +
            "AND ts.isCancelled = false " +
            "AND ts.startTime BETWEEN :start AND :end")
    boolean existsByTrainerAndTimeRange(
            @Param("trainerId") Integer trainerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
    @Query("SELECT ts FROM TrainingSession ts WHERE ts.client.id = :clientId AND ts.isCancelled = false")
    List<TrainingSession> findByClientId(@Param("clientId") Integer clientId);

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.trainer.id = :trainerId AND ts.isCancelled = false")
    List<TrainingSession> findByTrainerId(@Param("trainerId") Integer trainerId);
}