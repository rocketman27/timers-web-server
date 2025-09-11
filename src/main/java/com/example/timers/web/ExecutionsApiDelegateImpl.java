package com.example.timers.web;

import com.example.timers.api.ExecutionsApiDelegate;
import com.example.timers.domain.execution.TimerExecution;
import com.example.timers.repository.TimerExecutionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExecutionsApiDelegateImpl implements ExecutionsApiDelegate {

    private final TimerExecutionRepository repository;

    public ExecutionsApiDelegateImpl(TimerExecutionRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<List<com.example.timers.model.TimerExecution>> listExecutions(String instanceId, Integer page, Integer size) {
        int p = page == null ? 0 : page;
        int s = size == null ? 20 : size;
        var pageReq = PageRequest.of(p, s);
        List<TimerExecution> list = (instanceId == null || instanceId.isBlank())
                ? repository.findAll(pageReq).getContent()
                : repository.findByInstanceId(instanceId, pageReq).getContent();
        return ResponseEntity.ok(list.stream().map(this::toApi).collect(Collectors.toList()));
    }

    private com.example.timers.model.TimerExecution toApi(TimerExecution e) {
        com.example.timers.model.TimerExecution a = new com.example.timers.model.TimerExecution();
        a.setId(e.getId());
        a.setInstanceId(e.getInstanceId());
        a.setScheduledFor(e.getScheduledFor());
        a.setStartedAt(e.getStartedAt());
        a.setFinishedAt(e.getFinishedAt());
        a.setErrorMessage(e.getErrorMessage());
        if (e.getOutcome() != null) {
            a.setOutcome(com.example.timers.model.TimerExecution.OutcomeEnum.valueOf(e.getOutcome().name()));
        }
        if (e.getTriggerType() != null) {
            a.setTriggerType(com.example.timers.model.TimerExecution.TriggerTypeEnum.valueOf(e.getTriggerType().name()));
        }
        return a;
    }
}


