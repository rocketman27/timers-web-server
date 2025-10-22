package com.bnpparibas.tr.timer.web;

import com.bnpparibas.tr.timer.api.ExecutionsApiDelegate;
import com.bnpparibas.tr.timer.domain.execution.TimerExecution;
import com.bnpparibas.tr.timer.model.TimerExecutionDto;
import com.bnpparibas.tr.timer.repository.TimerExecutionRepository;
import org.springframework.data.domain.PageRequest;
import com.bnpparibas.tr.timer.service.TimerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExecutionsApiDelegateImpl implements ExecutionsApiDelegate {

    private final TimerExecutionRepository repository;
    private final TimerService templateService;

    public ExecutionsApiDelegateImpl(TimerExecutionRepository repository, TimerService templateService) {
        this.repository = repository;
        this.templateService = templateService;
    }

    @Override
    public ResponseEntity<List<TimerExecutionDto>> listExecutions(String instanceId, Integer page, Integer size) {
        int p = page == null ? 0 : page;
        int s = size == null ? 20 : size;
        var pageReq = PageRequest.of(p, s);
        List<TimerExecution> list = repository.findAll(pageReq).getContent();
        return ResponseEntity.ok(list.stream().map(this::toApi).collect(Collectors.toList()));
    }

    private TimerExecutionDto toApi(TimerExecution e) {
        TimerExecutionDto a = new TimerExecutionDto();
        a.setId(e.getId());
        a.setInstanceId(e.getInstanceId());
        a.setTimerId(e.getTimerId());
        if (e.getTimerId() != null) {
            templateService.get(e.getTimerId()).ifPresent(t -> a.setTimerName(t.getName()));
        }
        a.setScheduledFor(e.getScheduledFor());
        a.setStartedAt(e.getStartedAt());
        a.setFinishedAt(e.getFinishedAt());
        a.setErrorMessage(e.getErrorMessage());
        if (e.getOutcome() != null) {
            a.setOutcome(TimerExecutionDto.OutcomeEnum.valueOf(e.getOutcome().name()));
        }
        if (e.getTriggerType() != null) {
            a.setTriggerType(TimerExecutionDto.TriggerTypeEnum.valueOf(e.getTriggerType().name()));
        }
        return a;
    }
}


