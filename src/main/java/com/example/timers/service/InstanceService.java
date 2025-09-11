package com.example.timers.service;

import com.example.timers.domain.TimerStatus;
import com.example.timers.domain.instance.TimerInstance;
import com.example.timers.repository.TimerInstanceRepository;
import com.example.timers.scheduler.TimerScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class InstanceService {

    private static final Logger log = LoggerFactory.getLogger(InstanceService.class);
    private final TimerInstanceRepository repository;
    private final TimerScheduler scheduler;

    public InstanceService(TimerInstanceRepository repository, TimerScheduler scheduler) {
        this.repository = repository;
        this.scheduler = scheduler;
    }

    public List<TimerInstance> list(int page, int size) {
        // Minimal implementation: return paged results without filters for now
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<TimerInstance> suspend(Collection<String> ids) {
        List<TimerInstance> updated = new ArrayList<>();
        for (String id : ids) {
            repository.findById(id).ifPresent(inst -> {
                log.info("Suspending instance: {} (current status: {})", id, inst.getStatus());
                inst.setStatus(TimerStatus.SUSPENDED);
                // Pause the Quartz job
                try {
                    scheduler.pause(id);
                    log.info("Successfully paused Quartz job for instance: {}", id);
                } catch (Exception e) {
                    log.error("Failed to pause Quartz job for instance {}", id, e);
                }
                updated.add(inst);
            });
        }
        return repository.saveAll(updated);
    }

    public List<TimerInstance> resume(Collection<String> ids) {
        List<TimerInstance> updated = new ArrayList<>();
        for (String id : ids) {
            repository.findById(id).ifPresent(inst -> {
                log.info("Resuming instance: {} (current status: {})", id, inst.getStatus());
                inst.setStatus(TimerStatus.ACTIVE);
                // Resume the Quartz job
                try {
                    scheduler.resume(id);
                    log.info("Successfully resumed Quartz job for instance: {}", id);
                } catch (Exception e) {
                    log.error("Failed to resume Quartz job for instance {}", id, e);
                }
                updated.add(inst);
            });
        }
        return repository.saveAll(updated);
    }

    public List<TimerInstance> trigger(Collection<String> ids) {
        List<TimerInstance> updated = new ArrayList<>();
        for (String id : ids) {
            repository.findById(id).ifPresent(inst -> {
                // Only allow triggering if instance is in a triggerable state
                if (inst.getStatus() == TimerStatus.ACTIVE || 
                    inst.getStatus() == TimerStatus.COMPLETED || 
                    inst.getStatus() == TimerStatus.FAILED) {
                    
                    log.info("Triggering instance: {} (current status: {})", id, inst.getStatus());
                    inst.setLastAttemptAt(OffsetDateTime.now());
                    // Don't change status - keep it as is (ACTIVE/SUSPENDED for permission only)
                    // Execution state will be tracked in TimerExecution
                    repository.save(inst);
                    
                    // Trigger Quartz job now (scheduled under instance id)
                    try {
                        log.info("Calling scheduler.triggerNow for instance: {}", id);
                        scheduler.triggerNow(inst.getId());
                        log.info("Successfully triggered Quartz job for instance: {}", id);
                    } catch (Exception e) {
                        log.error("Failed to trigger Quartz job for instance: {}", id, e);
                        // Don't change status on error - let TimerExecution track the failure
                    }
                    updated.add(inst);
                } else {
                    log.warn("Cannot trigger instance: {} - status is not triggerable: {}", id, inst.getStatus());
                }
            });
        }
        return repository.saveAll(updated);
    }

    public List<TimerInstance> reset(Collection<String> ids) {
        List<TimerInstance> updated = new ArrayList<>();
        for (String id : ids) {
            repository.findById(id).ifPresent(inst -> {
                // Reset completed or failed instances back to ACTIVE
                if (inst.getStatus() == TimerStatus.COMPLETED || inst.getStatus() == TimerStatus.FAILED) {
                    log.info("Resetting instance: {} from {} to ACTIVE", id, inst.getStatus());
                    inst.setStatus(TimerStatus.ACTIVE);
                    updated.add(inst);
                } else {
                    log.debug("Instance: {} not reset - current status: {}", id, inst.getStatus());
                }
            });
        }
        return repository.saveAll(updated);
    }
}





