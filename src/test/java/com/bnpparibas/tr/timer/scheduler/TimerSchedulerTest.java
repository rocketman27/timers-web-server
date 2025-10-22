package com.bnpparibas.tr.timer.scheduler;

import com.bnpparibas.tr.timer.domain.timer.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

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
    void scheduleOrUpdateTimer_createsNew_whenNotExists() throws Exception {
        when(quartz.checkExists(any(JobKey.class))).thenReturn(false);

        Timer t = new Timer();
        t.setId("id1");
        t.setName("n");
        t.setZoneId("UTC");
        t.setTriggerTime(LocalTime.of(12, 0));

        scheduler.scheduleOrUpdateTimer(t);

        verify(quartz, never()).deleteJob(any());
        verify(quartz, times(1)).scheduleJob(any(), any());
    }

    @Test
    void scheduleOrUpdateTimer_replaces_whenExists() throws Exception {
        when(quartz.checkExists(any(JobKey.class))).thenReturn(true);

        Timer t = new Timer();
        t.setId("id2");
        t.setName("n");
        t.setZoneId("UTC");
        t.setTriggerTime(LocalTime.of(1, 5));

        scheduler.scheduleOrUpdateTimer(t);

        verify(quartz, times(1)).deleteJob(any(JobKey.class));
        verify(quartz, times(1)).scheduleJob(any(), any());
    }

    @Test
    void triggerNow_addsImmediateTrigger() throws SchedulerException {
        when(quartz.checkExists(any(TriggerKey.class))).thenReturn(false);
        when(quartz.checkExists(any(JobKey.class))).thenReturn(false);

        scheduler.triggerNow("abc");

        verify(quartz, times(1)).addJob(any(), eq(true));
        verify(quartz, times(1)).scheduleJob(any());
    }

    @Test
    void pause_and_resume_delegate() throws Exception {
        scheduler.pause("x");
        scheduler.resume("x");
        verify(quartz, times(1)).pauseJob(any(JobKey.class));
        verify(quartz, times(1)).resumeJob(any(JobKey.class));
    }
}


