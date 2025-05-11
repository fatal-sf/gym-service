package com.example.gym.repository;

import com.example.gym.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByIsActiveTrue();
    boolean existsByMembershipNumber(String membershipNumber);
}