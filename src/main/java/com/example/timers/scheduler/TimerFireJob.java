package com.example.timers.scheduler;

import com.example.timers.domain.instance.TimerInstance;
import com.example.timers.domain.TimerStatus;
import com.example.timers.domain.execution.TimerExecution;
import com.example.timers.domain.execution.TimerExecutionOutcome;
import com.example.timers.domain.execution.TriggerType;
import com.example.timers.repository.TimerInstanceRepository;
import com.example.timers.repository.TimerExecutionRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import com.example.timers.domain.event.TimerExecutionEvent;
import com.example.timers.service.KafkaEventService;

@Component
public class TimerFireJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(TimerFireJob.class);
    public static final String DATA_INSTANCE_ID = "instanceId";
    public static final String DATA_ZONE_ID = "zoneId";
    public static final String DATA_TRIGGER_TYPE = "triggerType";

    private final TimerInstanceRepository instanceRepository;
    private final TimerExecutionRepository executionRepository;
    private final KafkaEventService kafkaEventService;

    public TimerFireJob(TimerInstanceRepository instanceRepository, TimerExecutionRepository executionRepository, KafkaEventService kafkaEventService) {
        this.instanceRepository = instanceRepository;
        this.executionRepository = executionRepository;
        this.kafkaEventService = kafkaEventService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String instanceId = context.getMergedJobDataMap().getString(DATA_INSTANCE_ID);
        String zoneId = context.getMergedJobDataMap().getString(DATA_ZONE_ID);
        
        log.info("TimerFireJob executing for instance: {}, zone: {}", instanceId, zoneId);
        
        ZoneId zone = (zoneId == null || zoneId.isBlank()) ? ZoneId.systemDefault() : ZoneId.of(zoneId);
        ZonedDateTime now = ZonedDateTime.now(zone);
        
        // Load the timer instance
        TimerInstance instance = instanceRepository.findById(instanceId).orElse(null);
        if (instance == null) {
            log.warn("Instance {} not found, skipping execution", instanceId);
            return;
        }
        
        // Determine trigger type based on the trigger that fired this job
        TriggerType triggerType = determineTriggerType(context);
        
        // Check if execution is allowed (no daily limits - always allow)
        if (!canExecute(instance)) {
            log.info("Instance {} cannot execute, skipping", instanceId);
            recordExecution(instance, now, TimerExecutionOutcome.SKIPPED, null, triggerType);
            return;
        }
        
        // Execute the timer
        try {
            log.info("Executing timer for instance: {}", instanceId);
            

            
            // Execute business logic (placeholder for now)
            executeTimerBusinessLogic(instance);
            
            // Update TimerInstance with last success time (for business logic, not status)
            instance.setLastSuccessAt(now.toOffsetDateTime());
            instanceRepository.save(instance);
            
            // Publish timer execution event to Kafka with exclusion information
            publishTimerExecutionEvent(instance, now, triggerType);
            
            // Record successful execution completion
            recordExecution(instance, now, TimerExecutionOutcome.SUCCESS, null, triggerType);
            
            log.info("Instance {} completed successfully", instanceId);
            
        } catch (Exception e) {
            log.error("Failed to execute timer for instance: {}", instanceId, e);
            
            // Record failed execution completion
            recordExecution(instance, now, TimerExecutionOutcome.FAILED, e.getMessage(), triggerType);
            
            throw new JobExecutionException("Failed to execute timer job", e, false);
        }
    }

    /**
     * Publish timer execution event to Kafka with exclusion information
     */
    private void publishTimerExecutionEvent(TimerInstance instance, ZonedDateTime executionTime, TriggerType triggerType) {
        try {
            TimerExecutionEvent event = new TimerExecutionEvent(
                instance.getId(),
                instance.getTemplateId(),
                "Template-" + instance.getTemplateId(), // You might want to load the actual template name
                instance.getCountry(),
                instance.getRegion(),
                instance.getSubregion(),
                instance.getFlowType(),
                instance.getClientId(),
                instance.getProductType(),
                instance.getExcludedCountries(),
                instance.getExcludedRegions(),
                instance.getZoneId(),
                executionTime.toOffsetDateTime(),
                triggerType.name()
            );
            
            kafkaEventService.publishTimerExecution(event);
            log.debug("Published timer execution event to Kafka: {}", event);
            
        } catch (Exception e) {
            log.error("Failed to publish timer execution event to Kafka for instance: {}", instance.getId(), e);
            // Don't throw - we don't want timer execution to fail if Kafka publishing fails
        }
    }
    
    /**
     * Check if the instance can execute (no daily limits)
     */
    private boolean canExecute(TimerInstance instance) {
        // Remove daily execution limits - allow multiple executions per day
        // This makes the system more resilient to downstream failures and business needs
        
        // For now, always allow execution
        // TODO: Add configurable business rules here if needed later
        return true;
    }
    
    /**
     * Execute the actual timer business logic
     */
    private void executeTimerBusinessLogic(TimerInstance instance) {
        // TODO: Implement actual business logic
        // For now, just a placeholder that simulates work
        log.info("Executing business logic for instance: {}", instance.getId());
        
        // Simulate some work
        try {
            Thread.sleep(100); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Record execution details in TimerExecution
     */
    private void recordExecution(TimerInstance instance, ZonedDateTime now, 
                                TimerExecutionOutcome outcome, String errorMessage, 
                                TriggerType triggerType) {
        
        try {
            log.info("Creating execution record for instance: {} with outcome: {}", instance.getId(), outcome);
            
            TimerExecution execution = new TimerExecution();
            // Don't set ID manually - let Hibernate generate it
            execution.setInstanceId(instance.getId());
            execution.setScheduledFor(now.toOffsetDateTime());
            execution.setStartedAt(now.toOffsetDateTime());
            execution.setFinishedAt(now.toOffsetDateTime());
            execution.setOutcome(outcome);
            execution.setErrorMessage(errorMessage);
            execution.setTriggerType(triggerType);
            execution.setIdempotencyKey(instance.getId() + "_" + now.toLocalDate() + "_" + System.currentTimeMillis());
            
            log.info("Saving execution record for instance: {}", instance.getId());
            TimerExecution saved = executionRepository.save(execution);
            log.info("Successfully saved execution record: {} for instance: {}", saved.getId(), instance.getId());
            
        } catch (Exception e) {
            log.error("Failed to record execution for instance: {}", instance.getId(), e);
            throw e; // Re-throw to see the error in the main execution flow
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
                log.debug("Detected manual trigger for instance: {}", context.getMergedJobDataMap().getString(DATA_INSTANCE_ID));
                return TriggerType.MANUAL;
            } else {
                log.debug("Detected scheduled trigger for instance: {}", context.getMergedJobDataMap().getString(DATA_INSTANCE_ID));
                return TriggerType.SCHEDULED;
            }
        } catch (Exception e) {
            log.warn("Could not determine trigger type, defaulting to SCHEDULED", e);
            return TriggerType.SCHEDULED;
        }
    }
}


