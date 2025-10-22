package com.bnpparibas.tr.timer.scheduler;

import com.bnpparibas.tr.timer.domain.timer.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TimerSchedulerTest {

    private Scheduler quartz;
    private TimerScheduler scheduler;

    @BeforeEach
    void setup() {
        quartz = Mockito.mock(Scheduler.class);
        scheduler = new TimerScheduler(quartz);
    }

    @Test
    void scheduleOrUpdateTimer_createsOrReplacesJob_andSchedulesTrigger() throws Exception {
        when(quartz.checkExists(any(org.quartz.JobKey.class))).thenReturn(false);

        Timer timer = new Timer();
        timer.setId("id1");
        timer.setName("name");
        timer.setZoneId("UTC");
        timer.setTriggerTime(LocalTime.of(12, 0));

        scheduler.scheduleOrUpdateTimer(timer);

        verify(quartz, times(1)).scheduleJob(any(), any());
        verify(quartz, never()).deleteJob(any());
    }

    @Test
    void scheduleOrUpdateTimer_replacesExistingJob() throws Exception {
        when(quartz.checkExists(any(org.quartz.JobKey.class))).thenReturn(true);

        Timer timer = new Timer();
        timer.setId("id2");
        timer.setName("name");
        timer.setZoneId("UTC");
        timer.setTriggerTime(LocalTime.of(1, 5));

        scheduler.scheduleOrUpdateTimer(timer);

        verify(quartz, times(1)).deleteJob(any());
        verify(quartz, times(1)).scheduleJob(any(), any());
    }

    @Test
    void triggerNow_addsImmediateTrigger_andSchedules() throws SchedulerException {
        when(quartz.checkExists(any(org.quartz.TriggerKey.class))).thenReturn(false);

        scheduler.triggerNow("abc");

        verify(quartz, times(1)).addJob(any(), eq(true));
        verify(quartz, times(1)).scheduleJob(any());
    }
}


