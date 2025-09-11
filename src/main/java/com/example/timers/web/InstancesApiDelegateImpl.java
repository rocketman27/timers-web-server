package com.example.timers.web;

import com.example.timers.api.InstancesApiDelegate;
import com.example.timers.model.BatchIdsRequest;
import com.example.timers.model.TimerInstance;
import com.example.timers.service.InstanceService;
import com.example.timers.mapper.InstanceMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstancesApiDelegateImpl implements InstancesApiDelegate {

    private final InstanceService instanceService;

    public InstancesApiDelegateImpl(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @Override
    public ResponseEntity<List<TimerInstance>> listInstances(String templateId, String country, String region, String subregion, String flowType, String clientId, String productType, Integer page, Integer size) {
        int p = page == null ? 0 : page;
        int s = size == null ? 20 : size;
        var list = instanceService.list(p, s);
        return ResponseEntity.ok(InstanceMapper.toApiList(list));
    }

    @Override
    public ResponseEntity<List<TimerInstance>> resumeInstances(BatchIdsRequest batchIdsRequest) {
        var updated = instanceService.resume(batchIdsRequest.getIds());
        return ResponseEntity.ok(InstanceMapper.toApiList(updated));
    }

    @Override
    public ResponseEntity<List<TimerInstance>> suspendInstances(BatchIdsRequest batchIdsRequest) {
        var updated = instanceService.suspend(batchIdsRequest.getIds());
        return ResponseEntity.ok(InstanceMapper.toApiList(updated));
    }

    @Override
    public ResponseEntity<List<TimerInstance>> triggerInstances(BatchIdsRequest batchIdsRequest) {
        var updated = instanceService.trigger(batchIdsRequest.getIds());
        return ResponseEntity.ok(InstanceMapper.toApiList(updated));
    }

    @Override
    public ResponseEntity<List<TimerInstance>> resetInstances(BatchIdsRequest batchIdsRequest) {
        var updated = instanceService.reset(batchIdsRequest.getIds());
        return ResponseEntity.ok(InstanceMapper.toApiList(updated));
    }
}


