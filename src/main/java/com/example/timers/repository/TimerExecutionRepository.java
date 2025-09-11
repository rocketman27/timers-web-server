package com.example.timers.repository;

import com.example.timers.domain.execution.TimerExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerExecutionRepository extends JpaRepository<TimerExecution, String> {
    Page<TimerExecution> findByInstanceId(String instanceId, Pageable pageable);
}


