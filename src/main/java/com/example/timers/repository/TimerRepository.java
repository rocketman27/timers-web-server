package com.example.timers.repository;

import com.example.timers.domain.timer.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, String> { }



