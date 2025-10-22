package com.bnpparibas.tr.timer.scheduler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TimerSchedulerExtraTest {

    @Test
    void unschedule_deletesJob() throws Exception {
        Scheduler quartz = Mockito.mock(Scheduler.class);
        TimerScheduler scheduler = new TimerScheduler(quartz);
        scheduler.unschedule("toDel");
        verify(quartz, times(1)).deleteJob(any(JobKey.class));
    }

    @Test
    void scheduleOrUpdate_fullOverload_schedules() throws Exception {
        Scheduler quartz = Mockito.mock(Scheduler.class);
        TimerScheduler scheduler = new TimerScheduler(quartz);
        scheduler.scheduleOrUpdate(
                "idF", "0 0 12 * * ?", "UTC",
                "TemplateName", "US", "Americas", "North",
                "FX", "C1", "CASH",
                java.util.List.of("MX"), java.util.List.of("Antarctica")
        );
        verify(quartz, times(1)).scheduleJob(any(), any());
    }
}


