package com.example.timers.mapper;

import com.example.timers.domain.TimerStatus;

import java.util.List;
import java.util.stream.Collectors;

public class InstanceMapper {

    public static com.example.timers.model.TimerInstance toApi(com.example.timers.domain.instance.TimerInstance d) {
        com.example.timers.model.TimerInstance a = new com.example.timers.model.TimerInstance();
        a.setId(d.getId());
        a.setTemplateId(d.getTemplateId());
        a.setCountry(d.getCountry());
        a.setRegion(d.getRegion());
        a.setSubregion(d.getSubregion());
        a.setFlowType(d.getFlowType());
        a.setClientId(d.getClientId());
        a.setProductType(d.getProductType() != null && !d.getProductType().trim().isEmpty() ? 
            com.example.timers.model.TimerInstance.ProductTypeEnum.fromValue(d.getProductType()) : null);
        a.setZoneId(d.getZoneId());
        a.setLastSuccessAt(d.getLastSuccessAt());
        a.setLastAttemptAt(d.getLastAttemptAt());
        a.setAttemptCountToday(d.getAttemptCountToday());
        // Map all statuses correctly
        if (d.getStatus() != null) {
            switch (d.getStatus()) {
                case ACTIVE:
                    a.setStatus(com.example.timers.model.TimerInstance.StatusEnum.ACTIVE);
                    break;
                case SUSPENDED:
                    a.setStatus(com.example.timers.model.TimerInstance.StatusEnum.SUSPENDED);
                    break;
                case RUNNING:
                    a.setStatus(com.example.timers.model.TimerInstance.StatusEnum.RUNNING);
                    break;
                case COMPLETED:
                    a.setStatus(com.example.timers.model.TimerInstance.StatusEnum.COMPLETED);
                    break;
                case FAILED:
                    a.setStatus(com.example.timers.model.TimerInstance.StatusEnum.FAILED);
                    break;
                default:
                    a.setStatus(com.example.timers.model.TimerInstance.StatusEnum.SUSPENDED);
            }
        }
        return a;
    }

    public static List<com.example.timers.model.TimerInstance> toApiList(List<com.example.timers.domain.instance.TimerInstance> list) {
        return list.stream().map(InstanceMapper::toApi).collect(Collectors.toList());
    }
}


