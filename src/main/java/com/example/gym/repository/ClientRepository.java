package com.example.gym.repository;

import com.example.gym.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByIsActiveTrue();

    Client findByIdAndIsActiveTrue(Integer id);

    @Query("SELECT c FROM Client c WHERE " +
            "(c.name LIKE %:name% OR c.email LIKE %:email%) AND " +
            "c.isActive = true")
    List<Client> findByNameContainingOrEmailContainingAndIsActiveTrue(
            @Param("name") String name,
            @Param("email") String email);
}