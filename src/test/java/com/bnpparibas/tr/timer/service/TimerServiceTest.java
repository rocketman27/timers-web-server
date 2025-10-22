package com.bnpparibas.tr.timer.service;

import com.bnpparibas.tr.timer.domain.timer.Timer;
import com.bnpparibas.tr.timer.repository.TimerRepository;
import com.bnpparibas.tr.timer.scheduler.TimerScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.SchedulerException;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimerServiceTest {

    @Mock
    private TimerRepository timerRepository;

    @Mock
    private TimerScheduler timerScheduler;

    @InjectMocks
    private TimerService timerService;

    @BeforeEach
    void setup() {
        when(timerRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void saveAndSchedule_assignsId_whenMissing_andSchedulesActive() throws Exception {
        Timer timer = new Timer();
        timer.setName("t1");
        timer.setZoneId("UTC");
        timer.setTriggerTime(LocalTime.of(10, 15));
        timer.setSuspended(false);

        Timer saved = timerService.saveAndSchedule(timer);

        assertThat(saved.getId()).isNotBlank();
        verify(timerScheduler, times(1)).scheduleOrUpdateTimer(saved);
        verify(timerScheduler, never()).pause(any());
    }

    @Test
    void saveAndSchedule_keepsExistingId_andPausesWhenSuspended() throws Exception {
        Timer timer = new Timer();
        timer.setId("abc");
        timer.setName("t1");
        timer.setZoneId("UTC");
        timer.setTriggerTime(LocalTime.of(10, 15));
        timer.setSuspended(true);

        Timer saved = timerService.saveAndSchedule(timer);

        assertThat(saved.getId()).isEqualTo("abc");
        verify(timerScheduler, times(1)).scheduleOrUpdateTimer(saved);
        verify(timerScheduler, times(1)).pause("abc");
    }

    @Test
    void saveAndSchedule_wrapsSchedulerException() throws Exception {
        Timer timer = new Timer();
        timer.setName("t1");
        timer.setZoneId("UTC");
        timer.setTriggerTime(LocalTime.of(10, 15));
        doThrow(new SchedulerException("boom")).when(timerScheduler).scheduleOrUpdateTimer(any());

        assertThatThrownBy(() -> timerService.saveAndSchedule(timer))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to schedule timer");
    }
}


