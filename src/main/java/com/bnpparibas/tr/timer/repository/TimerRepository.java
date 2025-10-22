package com.bnpparibas.tr.timer.repository;

import com.bnpparibas.tr.timer.domain.timer.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, String> { }



