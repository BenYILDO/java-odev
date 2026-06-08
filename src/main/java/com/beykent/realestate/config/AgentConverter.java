package com.beykent.realestate.config;

import com.beykent.realestate.entity.Agent;
import com.beykent.realestate.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgentConverter implements Converter<String, Agent> {

    private final AgentRepository agentRepository;

    @Override
    public Agent convert(String source) {
        if (source == null || source.isBlank()) return null;
        return agentRepository.findById(Long.parseLong(source)).orElse(null);
    }
}
