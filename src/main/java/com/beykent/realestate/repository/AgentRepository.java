package com.beykent.realestate.repository;

import com.beykent.realestate.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    List<Agent> findByOfisContainingIgnoreCase(String ofis);

    boolean existsByEmail(String email);
}
