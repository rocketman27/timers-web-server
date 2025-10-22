package com.bnpparibas.tr.timer.web;

import com.bnpparibas.tr.timer.domain.timer.Timer;
import com.bnpparibas.tr.timer.model.CreateTimerRequestDto;
import com.bnpparibas.tr.timer.model.TimerDto;
import com.bnpparibas.tr.timer.service.GeoCatalogService;
import com.bnpparibas.tr.timer.service.TimerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TimersApiDelegateImplTest {

    private TimerService timerService;
    private GeoCatalogService geoCatalogService;
    private TimersApiDelegateImpl delegate;

    @BeforeEach
    void setup() {
        timerService = Mockito.mock(TimerService.class);
        geoCatalogService = Mockito.mock(GeoCatalogService.class);
        delegate = new TimersApiDelegateImpl(timerService, geoCatalogService);
        when(geoCatalogService.isValidCountryCode(any())).thenReturn(true);
        when(geoCatalogService.isValidRegionCode(any())).thenReturn(true);
    }

    @Test
    void createTimer_validatesZone_andSchedules() {
        CreateTimerRequestDto req = new CreateTimerRequestDto();
        req.setName("n");
        req.setZoneId("UTC");
        req.setTriggerTime("10:15");
        Timer saved = timer("id1", req);
        when(timerService.saveAndSchedule(any())).thenReturn(saved);

        ResponseEntity<TimerDto> resp = delegate.createTimer(req);

        assertThat(resp.getStatusCode().value()).isEqualTo(201);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().getId()).isEqualTo("id1");
    }

    @Test
    void createTimer_invalidZone_throws400() {
        CreateTimerRequestDto req = new CreateTimerRequestDto();
        req.setName("n");
        req.setZoneId("Invalid/Zone");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> delegate.createTimer(req));
        assertThat(ex.getMessage()).contains("zoneId");
        verifyNoInteractions(timerService);
    }

    @Test
    void getTimer_notFound_returns404() {
        when(timerService.get("x")).thenReturn(Optional.empty());
        ResponseEntity<TimerDto> resp = delegate.getTimer("x");
        assertThat(resp.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void listTimers_mapsAll() {
        Timer t = new Timer();
        t.setId("i");
        t.setName("n");
        t.setZoneId("UTC");
        when(timerService.list()).thenReturn(List.of(t));

        ResponseEntity<List<TimerDto>> resp = delegate.listTimers();
        assertThat(resp.getStatusCode().value()).isEqualTo(200);
        assertThat(resp.getBody()).hasSize(1);
        assertThat(resp.getBody().get(0).getId()).isEqualTo("i");
    }

    @Test
    void updateTimer_updatesExisting() {
        CreateTimerRequestDto req = new CreateTimerRequestDto();
        req.setName("n2");
        req.setZoneId("UTC");
        req.setTriggerTime("12:00");
        Timer existing = new Timer();
        existing.setId("id1");
        existing.setName("n");
        existing.setZoneId("UTC");
        when(timerService.get("id1")).thenReturn(Optional.of(existing));
        when(timerService.saveAndSchedule(any())).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<TimerDto> resp = delegate.updateTimer("id1", req);
        assertThat(resp.getStatusCode().value()).isEqualTo(200);

        ArgumentCaptor<Timer> captor = ArgumentCaptor.forClass(Timer.class);
        verify(timerService).saveAndSchedule(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("n2");
        assertThat(captor.getValue().getTriggerTime()).isEqualTo(LocalTime.of(12, 0));
    }

    private static Timer timer(String id, CreateTimerRequestDto req) {
        Timer t = new Timer();
        t.setId(id);
        t.setName(req.getName());
        t.setZoneId(req.getZoneId());
        if (req.getTriggerTime() != null) t.setTriggerTime(LocalTime.parse(req.getTriggerTime()));
        return t;
    }
}


