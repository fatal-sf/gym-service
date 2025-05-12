package com.example.gym.repository;


import com.example.gym.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    @Query("SELECT t FROM Trainer t WHERE t.id = :id AND t.isActive = true")
    Optional<Trainer> findByIdAndIsActiveTrue(@Param("id") Integer id);

    @Query("SELECT t FROM Trainer t WHERE t.isActive = true")
    List<Trainer> findAllActive();

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Trainer t WHERE t.email = :email")
    boolean existsByEmail(@Param("email") String email);
    @Query("SELECT t FROM Trainer t WHERE t.isActive = true")
    List<Trainer> findByIsActiveTrue();
}