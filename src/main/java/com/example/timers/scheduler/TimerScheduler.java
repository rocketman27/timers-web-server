package com.example.timers.scheduler;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimeZone;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class TimerScheduler {

    private static final Logger log = LoggerFactory.getLogger(TimerScheduler.class);
    private final Scheduler scheduler;

    public TimerScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void scheduleOrUpdate(String id, String cronExpression, String zoneId, 
                                String templateName, String country, String region, 
                                String subregion, String flowType, String clientId, 
                                String productType) throws SchedulerException {
        scheduleOrUpdate(id, cronExpression, zoneId, templateName, country, region, subregion, flowType, clientId, productType, null, null);
    }

    public void scheduleOrUpdate(String id, String cronExpression, String zoneId, 
                                String templateName, String country, String region, 
                                String subregion, String flowType, String clientId, 
                                String productType, List<String> excludedCountries, 
                                List<String> excludedRegions) throws SchedulerException {
        JobKey jobKey = jobKey(id);

        JobDetail jobDetail = newJob(TimerFireJob.class)
                .withIdentity(jobKey)
                .withDescription(buildHumanReadableDescription(templateName, country, region, subregion, flowType, clientId, productType, cronExpression, zoneId, excludedCountries, excludedRegions))
                .usingJobData(TimerFireJob.DATA_INSTANCE_ID, id)
                .usingJobData(TimerFireJob.DATA_ZONE_ID, zoneId)
                .build();

        CronScheduleBuilder scheduleBuilder = cronSchedule(cronExpression)
                .inTimeZone(TimeZone.getTimeZone(zoneId))
                .withMisfireHandlingInstructionDoNothing();

        Trigger trigger = newTrigger()
                .withIdentity(triggerKey(id))
                .withDescription(buildHumanReadableDescription(templateName, country, region, subregion, flowType, clientId, productType, cronExpression, zoneId, excludedCountries, excludedRegions))
                .forJob(jobDetail)
                .withSchedule(scheduleBuilder)
                .build();

        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        scheduler.scheduleJob(jobDetail, trigger);
    }

    // Simple overload for backward compatibility
    public void scheduleOrUpdate(String id, String cronExpression, String zoneId) throws SchedulerException {
        scheduleOrUpdate(id, cronExpression, zoneId, "Unknown", "", "", "", "", "", "");
    }

    public void pause(String id) throws SchedulerException {
        scheduler.pauseJob(jobKey(id));
    }

    public void resume(String id) throws SchedulerException {
        scheduler.resumeJob(jobKey(id));
    }

    public void triggerNow(String id) throws SchedulerException {
        log.info("Manually triggering job for instance: {}", id);
        
        // Create a one-time trigger for immediate execution
        // This ensures the execution is recorded in QRTZ_FIRED_TRIGGERS
        TriggerKey immediateTriggerKey = new TriggerKey(id + "_immediate", "instances");
        
        // Delete any existing immediate trigger for this instance
        if (scheduler.checkExists(immediateTriggerKey)) {
            log.debug("Removing existing immediate trigger for instance: {}", id);
            scheduler.unscheduleJob(immediateTriggerKey);
        }
        
        // Create a one-time trigger that fires immediately
        Trigger immediateTrigger = newTrigger()
                .withIdentity(immediateTriggerKey)
                .forJob(jobKey(id))
                .withDescription("Manual trigger for instance: " + id)
                .startNow()
                .build();
        
        // Schedule the immediate trigger
        scheduler.scheduleJob(immediateTrigger);
        log.info("Scheduled immediate trigger for instance: {}", id);
    }

    public void unschedule(String id) throws SchedulerException {
        scheduler.deleteJob(jobKey(id));
    }

    private static JobKey jobKey(String id) {
        return new JobKey(id, "instances");
    }

    private static TriggerKey triggerKey(String id) {
        return new TriggerKey(id, "instances");
    }

    private String buildHumanReadableDescription(String templateName, String country, String region, 
                                               String subregion, String flowType, String clientId, 
                                               String productType, String cronExpression, String zoneId,
                                               List<String> excludedCountries, List<String> excludedRegions) {
        StringBuilder desc = new StringBuilder();
        desc.append("Template: ").append(templateName);
        if (country != null && !country.isEmpty()) desc.append(", Country: ").append(country);
        if (region != null && !region.isEmpty()) desc.append(", Region: ").append(region);
        if (subregion != null && !subregion.isEmpty()) desc.append(", Subregion: ").append(subregion);
        if (flowType != null && !flowType.isEmpty()) desc.append(", Flow: ").append(flowType);
        if (clientId != null && !clientId.isEmpty()) desc.append(", Client: ").append(clientId);
        if (productType != null && !productType.isEmpty()) desc.append(", Product: ").append(productType);
        
        // Add exclusion information
        if (excludedCountries != null && !excludedCountries.isEmpty()) {
            desc.append(", Excluded Countries: ").append(String.join(", ", excludedCountries));
        }
        if (excludedRegions != null && !excludedRegions.isEmpty()) {
            desc.append(", Excluded Regions: ").append(String.join(", ", excludedRegions));
        }
        
        desc.append(", Cron: ").append(cronExpression).append(", Zone: ").append(zoneId);
        return desc.toString();
    }
}


