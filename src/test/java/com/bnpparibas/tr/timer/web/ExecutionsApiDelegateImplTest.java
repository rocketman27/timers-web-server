package com.bnpparibas.tr.timer.web;

import com.bnpparibas.tr.timer.domain.execution.TimerExecution;
import com.bnpparibas.tr.timer.domain.execution.TimerExecutionOutcome;
import com.bnpparibas.tr.timer.domain.execution.TriggerType;
import com.bnpparibas.tr.timer.domain.timer.Timer;
import com.bnpparibas.tr.timer.model.TimerExecutionDto;
import com.bnpparibas.tr.timer.repository.TimerExecutionRepository;
import com.bnpparibas.tr.timer.service.TimerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExecutionsApiDelegateImplTest {

    @Test
    void listExecutions_mapsEntities_toDto_and_resolvesTimerName() {
        TimerExecutionRepository repo = Mockito.mock(TimerExecutionRepository.class);
        TimerService timerService = Mockito.mock(TimerService.class);
        ExecutionsApiDelegateImpl delegate = new ExecutionsApiDelegateImpl(repo, timerService);

        TimerExecution e = new TimerExecution();
        e.setId("e1");
        e.setInstanceId(null);
        e.setTimerId("t1");
        e.setScheduledFor(OffsetDateTime.now());
        e.setStartedAt(OffsetDateTime.now());
        e.setFinishedAt(OffsetDateTime.now());
        e.setOutcome(TimerExecutionOutcome.SUCCESS);
        e.setTriggerType(TriggerType.SCHEDULED);
        Page<TimerExecution> page = new PageImpl<>(List.of(e), PageRequest.of(0, 20), 1);
        when(repo.findAll(any(PageRequest.class))).thenReturn(page);

        Timer t = new Timer();
        t.setId("t1");
        t.setName("Timer Name");
        when(timerService.get("t1")).thenReturn(Optional.of(t));

        ResponseEntity<List<TimerExecutionDto>> resp = delegate.listExecutions(null, 0, 20);
        assertThat(resp.getStatusCode().value()).isEqualTo(200);
        assertThat(resp.getBody()).hasSize(1);
        TimerExecutionDto dto = resp.getBody().get(0);
        assertThat(dto.getId()).isEqualTo("e1");
        assertThat(dto.getTimerId()).isEqualTo("t1");
        assertThat(dto.getTimerName()).isEqualTo("Timer Name");
        assertThat(dto.getOutcome()).isEqualTo(TimerExecutionDto.OutcomeEnum.SUCCESS);
        assertThat(dto.getTriggerType()).isEqualTo(TimerExecutionDto.TriggerTypeEnum.SCHEDULED);
    }
}


