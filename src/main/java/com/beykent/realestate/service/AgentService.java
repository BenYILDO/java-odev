package com.beykent.realestate.service;

import com.beykent.realestate.entity.Agent;
import com.beykent.realestate.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;

    public List<Agent> tumDanismanlar() {
        return agentRepository.findAll();
    }

    public Optional<Agent> danismanBul(Long id) {
        return agentRepository.findById(id);
    }

    public Agent danismanKaydet(Agent agent) {
        return agentRepository.save(agent);
    }

    public void danismanSil(Long id) {
        agentRepository.deleteById(id);
    }

    public boolean emailMevcut(String email) {
        return agentRepository.existsByEmail(email);
    }

    public long toplamDanisman() {
        return agentRepository.count();
    }
}
