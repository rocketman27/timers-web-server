package com.bnpparibas.tr.timers.scheduler;

import com.bnpparibas.tr.timers.domain.timer.Timer;
import com.bnpparibas.tr.timers.domain.execution.TimerExecution;
import com.bnpparibas.tr.timers.domain.execution.TimerExecutionOutcome;
import com.bnpparibas.tr.timers.domain.execution.TriggerType;
import com.bnpparibas.tr.timers.repository.TimerRepository;
import com.bnpparibas.tr.timers.repository.TimerExecutionRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.bnpparibas.tr.timers.domain.event.FilterCondition;
import com.bnpparibas.tr.timers.domain.event.TimerEvent;
import com.bnpparibas.tr.timers.service.KafkaEventService;

@Component
public class TimerFireJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(TimerFireJob.class);
    public static final String DATA_TIMER_ID = "timerId";
    public static final String DATA_ZONE_ID = "zoneId";
    public static final String DATA_TRIGGER_TYPE = "triggerType";

    private final TimerRepository timerRepository;
    private final TimerExecutionRepository executionRepository;
    private final KafkaEventService kafkaEventService;

    public TimerFireJob(TimerRepository timerRepository, TimerExecutionRepository executionRepository, KafkaEventService kafkaEventService) {
        this.timerRepository = timerRepository;
        this.executionRepository = executionRepository;
        this.kafkaEventService = kafkaEventService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String timerId = context.getMergedJobDataMap().getString(DATA_TIMER_ID);
        String zoneId = context.getMergedJobDataMap().getString(DATA_ZONE_ID);
        
        log.info("TimerFireJob executing for timer: {}, zone: {}", timerId, zoneId);
        
        ZoneId zone = (zoneId == null || zoneId.isBlank()) ? ZoneId.systemDefault() : ZoneId.of(zoneId);
        ZonedDateTime now = ZonedDateTime.now(zone);
        
        // Load Timer (preferred) or legacy Instance
        Timer timer = null;
        if (timerId != null && !timerId.isBlank()) {
            timer = timerRepository.findById(timerId).orElse(null);
            if (timer == null) {
                log.warn("Timer {} not found, skipping execution", timerId);
                return;
            }
        }
        // no legacy instance path
        
        // Determine trigger type based on the trigger that fired this job
        TriggerType triggerType = determineTriggerType(context);
        
        // Check if execution is allowed (no daily limits - always allow)
        // Skip checks removed; always allow for timer path
        
        // Execute the timer
        try {
            log.info("Executing timer for timerId: {}", timer.getId());
            

            
            // Execute business logic (placeholder for now)
            executeTimerBusinessLogic(timer);
            
            // Update TimerInstance with last success time (for business logic, not status)
            // no per-instance state to persist
            
            // Publish timer execution event to Kafka with exclusion information
            publishTimerExecutionEvent(timer, now, triggerType);
            
            // Record successful execution completion
            recordExecution(timer, now, TimerExecutionOutcome.SUCCESS, null, triggerType);
            
            log.info("Timer {} completed successfully", timerId);
            
        } catch (Exception e) {
            log.error("Failed to execute timer for timerId: {}", timerId, e);
            
            // Record failed execution completion
            recordExecution(timer, now, TimerExecutionOutcome.FAILED, e.getMessage(), triggerType);
            
            throw new JobExecutionException("Failed to execute timer job", e, false);
        }
    }

    /**
     * Publish timer execution event to Kafka with exclusion information
     */
    // removed legacy instance publisher

    private void publishTimerExecutionEvent(Timer timer, ZonedDateTime executionTime, TriggerType triggerType) {
        try {
            // Backward-compatible execution event (existing consumers)
            /*TimerExecutionEvent executionEvent = new TimerExecutionEvent(
                timer.getId(),
                timer.getId(),
                timer.getName(),
                null,
                null,
                null,
                null,
                null,
                null,
                timer.getExcludedCountries(),
                timer.getExcludedRegions(),
                timer.getZoneId(),
                executionTime.toOffsetDateTime(),
                triggerType.name()
            );
            kafkaEventService.publishTimerExecution(executionEvent);
            log.debug("Published timer execution event to Kafka: {}", executionEvent);*/

            // New shape for downstream service (Timer + FilterCondition)
            FilterCondition fc = new FilterCondition();
            fc.setRegions(timer.getRegions());
            fc.setCountries(timer.getCountries());
            fc.setProductTypes(timer.getProductTypes());
            fc.setFlowTypes(timer.getFlowTypes());
            // bookingModels not present in our domain yet; leave empty
            TimerEvent timerEvent = new TimerEvent(timer.getName(), "default", fc);
            kafkaEventService.publishTimerExecution(timerEvent);
            log.debug("Published timer event (new shape) to Kafka: {}", timerEvent);
        } catch (Exception e) {
            log.error("Failed to publish timer execution event to Kafka for timer: {}", timer.getId(), e);
        }
    }
    
    /**
     * Check if the instance can execute (no daily limits)
     */
    // no canExecute in timer-only model
    
    /**
     * Execute the actual timer business logic
     */
    // removed legacy instance business logic

    private void executeTimerBusinessLogic(Timer timer) {
        log.info("Executing business logic for timer: {}", timer.getId());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Record execution details in TimerExecution
     */
    // removed legacy instance execution record

    private void recordExecution(Timer timer, ZonedDateTime now,
                                TimerExecutionOutcome outcome, String errorMessage,
                                TriggerType triggerType) {
        try {
            log.info("Creating execution record for timer: {} with outcome: {}", timer.getId(), outcome);
            TimerExecution execution = new TimerExecution();
            execution.setInstanceId(null);
            execution.setTimerId(timer.getId());
            execution.setScheduledFor(now.toOffsetDateTime());
            execution.setStartedAt(now.toOffsetDateTime());
            execution.setFinishedAt(now.toOffsetDateTime());
            execution.setOutcome(outcome);
            execution.setErrorMessage(errorMessage);
            execution.setTriggerType(triggerType);
            execution.setIdempotencyKey(timer.getId() + "_" + now.toLocalDate() + "_" + System.currentTimeMillis());
            TimerExecution saved = executionRepository.save(execution);
            log.info("Successfully saved execution record: {} for timer: {}", saved.getId(), timer.getId());
        } catch (Exception e) {
            log.error("Failed to record execution for timer: {}", timer.getId(), e);
            throw e;
        }
    }
    
    /**
     * Determine the trigger type based on the job execution context
     */
    private TriggerType determineTriggerType(JobExecutionContext context) {
        try {
            // Check if this was triggered by an immediate trigger (manual trigger)
            // Immediate triggers have keys ending with "_immediate"
            String triggerKey = context.getTrigger().getKey().getName();
            if (triggerKey.endsWith("_immediate")) {
                log.debug("Detected manual trigger for timer: {}", context.getMergedJobDataMap().getString(DATA_TIMER_ID));
                return TriggerType.MANUAL;
            } else {
                log.debug("Detected scheduled trigger for timer: {}", context.getMergedJobDataMap().getString(DATA_TIMER_ID));
                return TriggerType.SCHEDULED;
            }
        } catch (Exception e) {
            log.warn("Could not determine trigger type, defaulting to SCHEDULED", e);
            return TriggerType.SCHEDULED;
        }
    }
}


