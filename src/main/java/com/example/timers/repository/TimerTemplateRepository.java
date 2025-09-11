package com.example.timers.repository;

import com.example.timers.domain.template.TimerTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerTemplateRepository extends JpaRepository<TimerTemplate, String> { }


