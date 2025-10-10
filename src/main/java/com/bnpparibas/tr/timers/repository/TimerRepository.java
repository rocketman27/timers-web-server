package com.bnpparibas.tr.timers.repository;

import com.bnpparibas.tr.timers.domain.timer.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, String> { }



