package com.bnpparibas.tr.timer.repository;

import com.bnpparibas.tr.timer.domain.execution.TimerExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerExecutionRepository extends JpaRepository<TimerExecution, String> {
    Page<TimerExecution> findByInstanceId(String instanceId, Pageable pageable);
    Page<TimerExecution> findByTimerId(String timerId, Pageable pageable);
}


