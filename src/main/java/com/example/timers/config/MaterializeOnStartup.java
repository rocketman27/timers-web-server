package com.example.timers.config;

import com.example.timers.domain.template.TimerTemplate;
import com.example.timers.repository.TimerTemplateRepository;
import com.example.timers.service.TemplateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaterializeOnStartup {

    @Bean
    CommandLineRunner materializeAllTemplates(TemplateService templateService,
                                              TimerTemplateRepository templateRepository) {
        return args -> {
            for (TimerTemplate tpl : templateRepository.findAll()) {
                templateService.updateAndRematerialize(tpl);
            }
        };
    }
}






