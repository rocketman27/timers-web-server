package com.example.timers.service;

import com.example.timers.domain.template.TimerTemplate;
import com.example.timers.repository.TimerTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TemplateService {

    private final TimerTemplateRepository templates;
    private final TemplateMaterializer materializer;

    public TemplateService(TimerTemplateRepository templates, TemplateMaterializer materializer) {
        this.templates = templates;
        this.materializer = materializer;
    }

    public List<TimerTemplate> list() {
        return templates.findAll();
    }

    public Optional<TimerTemplate> get(String id) {
        return templates.findById(id);
    }

    public TimerTemplate createAndMaterialize(TimerTemplate template) {
        if (template.getId() == null || template.getId().isBlank()) {
            template.setId(UUID.randomUUID().toString());
        }
        TimerTemplate saved = templates.save(template);
        materializer.materializeAndSchedule(saved);
        return saved;
    }

    public TimerTemplate updateAndRematerialize(TimerTemplate template) {
        TimerTemplate saved = templates.save(template);
        materializer.materializeAndSchedule(saved);
        return saved;
    }
}






