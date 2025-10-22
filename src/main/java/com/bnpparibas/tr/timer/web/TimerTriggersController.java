package com.bnpparibas.tr.timer.web;

import com.bnpparibas.tr.timer.scheduler.TimerScheduler;
import com.bnpparibas.tr.timer.model.BatchIdsRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timers")
public class TimerTriggersController {

    private static final Logger log = LoggerFactory.getLogger(TimerTriggersController.class);
    private final TimerScheduler scheduler;

    public TimerTriggersController(TimerScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping(path = "/{id}/_trigger")
    public ResponseEntity<Void> triggerSingle(@PathVariable("id") String id) {
        try {
            scheduler.triggerNow(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to trigger timer {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(path = "_trigger")
    public ResponseEntity<Void> triggerBatch(@RequestBody BatchIdsRequestDto request) {
        try {
            if (request != null && request.getIds() != null) {
                for (String id : request.getIds()) {
                    try {
                        scheduler.triggerNow(id);
                    } catch (Exception ex) {
                        log.warn("Failed to trigger timer {} in batch", id, ex);
                    }
                }
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to trigger timers batch", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}





