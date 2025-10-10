package com.bnpparibas.tr.timers.repository;

import com.bnpparibas.tr.timers.domain.execution.TimerExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerExecutionRepository extends JpaRepository<TimerExecution, String> {
    Page<TimerExecution> findByInstanceId(String instanceId, Pageable pageable);
    Page<TimerExecution> findByTimerId(String timerId, Pageable pageable);
}


