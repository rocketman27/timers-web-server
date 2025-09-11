package com.example.timers.repository;

import com.example.timers.domain.instance.TimerInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerInstanceRepository extends JpaRepository<TimerInstance, String> { }


