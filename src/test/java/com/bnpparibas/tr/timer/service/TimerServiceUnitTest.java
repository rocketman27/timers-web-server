package com.bnpparibas.tr.timer.service;

import com.bnpparibas.tr.timer.domain.timer.Timer;
import com.bnpparibas.tr.timer.repository.TimerRepository;
import com.bnpparibas.tr.timer.scheduler.TimerScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimerServiceUnitTest {

    @Mock
    private TimerRepository timerRepository;
    @Mock
    private TimerScheduler timerScheduler;
    @InjectMocks
    private TimerService service;

    @BeforeEach
    void setup() {
        when(timerRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void saveAndSchedule_active_callsScheduleOnly() throws Exception {
        Timer t = new Timer();
        t.setName("n");
        t.setZoneId("UTC");
        t.setTriggerTime(LocalTime.of(10, 0));
        t.setSuspended(false);

        Timer saved = service.saveAndSchedule(t);

        assertThat(saved.getId()).isNotBlank();
        verify(timerScheduler, times(1)).scheduleOrUpdateTimer(any());
        verify(timerScheduler, never()).pause(any());
    }

    @Test
    void saveAndSchedule_suspended_callsPause() throws Exception {
        Timer t = new Timer();
        t.setId("i");
        t.setName("n");
        t.setZoneId("UTC");
        t.setTriggerTime(LocalTime.of(10, 0));
        t.setSuspended(true);

        service.saveAndSchedule(t);

        verify(timerScheduler, times(1)).scheduleOrUpdateTimer(any());
        verify(timerScheduler, times(1)).pause("i");
    }
}


