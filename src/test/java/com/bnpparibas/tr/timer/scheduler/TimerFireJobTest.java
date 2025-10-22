package com.bnpparibas.tr.timer.scheduler;

import com.bnpparibas.tr.timer.domain.execution.TimerExecutionOutcome;
import com.bnpparibas.tr.timer.domain.timer.Timer;
import com.bnpparibas.tr.timer.repository.TimerExecutionRepository;
import com.bnpparibas.tr.timer.repository.TimerRepository;
import com.bnpparibas.tr.timer.service.KafkaEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

import java.time.LocalTime;

import static org.mockito.Mockito.*;

class TimerFireJobTest {

    private TimerRepository timerRepository;
    private TimerExecutionRepository executionRepository;
    private KafkaEventService kafkaEventService;
    private TimerFireJob job;

    @BeforeEach
    void setup() {
        timerRepository = mock(TimerRepository.class);
        executionRepository = mock(TimerExecutionRepository.class);
        kafkaEventService = mock(KafkaEventService.class);
        job = new TimerFireJob(timerRepository, executionRepository, kafkaEventService);

        // Ensure save() returns a non-null entity to avoid NPE in logging saved.getId()
        when(executionRepository.save(any())).thenAnswer(inv -> {
            com.bnpparibas.tr.timer.domain.execution.TimerExecution te = inv.getArgument(0);
            if (te.getId() == null) {
                te.setId("e1");
            }
            return te;
        });
    }

    @Test
    void execute_recordsSuccess_whenTimerExists() throws Exception {
        Timer t = new Timer();
        t.setId("id1");
        t.setName("N");
        t.setZoneId("UTC");
        t.setTriggerTime(LocalTime.of(1, 0));
        when(timerRepository.findById("id1")).thenReturn(java.util.Optional.of(t));

        JobExecutionContext ctx = mock(JobExecutionContext.class);
        Trigger trigger = mock(Trigger.class);
        when(trigger.getKey()).thenReturn(new org.quartz.TriggerKey("id1", "instances"));
        when(ctx.getTrigger()).thenReturn(trigger);
        JobDetail jobDetail = mock(JobDetail.class);
        when(ctx.getJobDetail()).thenReturn(jobDetail);
        JobDataMap map = new JobDataMap();
        map.put(TimerFireJob.DATA_TIMER_ID, "id1");
        map.put(TimerFireJob.DATA_ZONE_ID, "UTC");
        when(ctx.getMergedJobDataMap()).thenReturn(map);

        job.execute(ctx);

        // Verify a record was attempted to be saved
        verify(executionRepository, atLeastOnce()).save(argThat(exec -> exec.getTimerId().equals("id1") && exec.getOutcome() == TimerExecutionOutcome.SUCCESS));
    }

    @Test
    void execute_detectsManualTrigger() throws Exception {
        Timer t = new Timer();
        t.setId("id1");
        t.setName("N");
        t.setZoneId("UTC");
        when(timerRepository.findById("id1")).thenReturn(java.util.Optional.of(t));

        JobExecutionContext ctx = mock(JobExecutionContext.class);
        Trigger trigger = mock(Trigger.class);
        when(trigger.getKey()).thenReturn(new org.quartz.TriggerKey("id1_immediate", "instances"));
        when(ctx.getTrigger()).thenReturn(trigger);
        JobDetail jobDetail = mock(JobDetail.class);
        when(ctx.getJobDetail()).thenReturn(jobDetail);
        JobDataMap map = new JobDataMap();
        map.put(TimerFireJob.DATA_TIMER_ID, "id1");
        when(ctx.getMergedJobDataMap()).thenReturn(map);

        job.execute(ctx);

        verify(executionRepository, atLeastOnce()).save(argThat(exec -> exec.getTimerId().equals("id1")));
    }
}


