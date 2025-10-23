package com.bnpparibas.tr.timer.service;

import com.bnpparibas.tr.timer.domain.timer.Timer;
import com.bnpparibas.tr.timer.repository.TimerRepository;
import org.springframework.stereotype.Service;
import com.bnpparibas.tr.timer.scheduler.TimerScheduler;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TimerService {
    private final TimerRepository timers;
    private final TimerScheduler timerScheduler;

    public TimerService(TimerRepository timers, TimerScheduler timerScheduler) {
        this.timers = timers;
        this.timerScheduler = timerScheduler;
    }

    public List<Timer> list() {
        return timers.findAll();
    }

    public Optional<Timer> get(String id) {
        return timers.findById(id);
    }

    public boolean delete(String id) {
        if (timers.existsById(id)) {
            timers.deleteById(id);
            try {
                timerScheduler.unschedule(id);
            } catch (SchedulerException ignored) { }
            return true;
        }
        return false;
    }

    public Timer saveAndSchedule(Timer timer) {
        if (timer.getId() == null || timer.getId().isBlank()) {
            timer.setId(UUID.randomUUID().toString());
        }
        Timer saved = timers.save(timer);
        try {
            if (saved.isSuspended()) {
                timerScheduler.scheduleOrUpdateTimer(saved);
                timerScheduler.pause(saved.getId());
            } else {
                timerScheduler.scheduleOrUpdateTimer(saved);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to schedule timer " + saved.getId(), e);
        }
        return saved;
    }
}
