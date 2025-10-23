package com.bnpparibas.tr.timer.web;

import com.bnpparibas.tr.timer.api.TimersApiDelegate;
import com.bnpparibas.tr.timer.domain.timer.Timer;
import com.bnpparibas.tr.timer.model.CreateTimerRequestDto;
import com.bnpparibas.tr.timer.model.TimerDto;
import com.bnpparibas.tr.timer.service.TimerService;
import com.bnpparibas.tr.timer.service.GeoCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimersApiDelegateImpl implements TimersApiDelegate {
    private final TimerService timerService;
    private final GeoCatalogService geoCatalogService;

    public TimersApiDelegateImpl(TimerService timerService, GeoCatalogService geoCatalogService) {
        this.timerService = timerService;
        this.geoCatalogService = geoCatalogService;
    }

    @Override
    public ResponseEntity<TimerDto> createTimer(CreateTimerRequestDto req) {
        validateGeo(req);
        Timer saved = timerService.saveAndSchedule(fromDto(req, new Timer()));
        return ResponseEntity.status(201).body(toApi(saved));
    }

    @Override
    public ResponseEntity<TimerDto> getTimer(String id) {
        return timerService.get(id)
                           .map(this::toApi)
                           .map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<TimerDto>> listTimers() {
        return ResponseEntity.ok(timerService.list().stream().map(this::toApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TimerDto> updateTimer(String id, CreateTimerRequestDto req) {
        return timerService.get(id)
                           .map(existing -> {
                               validateGeo(req);
                               Timer saved = timerService.saveAndSchedule(fromDto(req, existing));
                               return ResponseEntity.ok(toApi(saved));
                           })
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteTimer(String id) {
        boolean removed = timerService.delete(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private TimerDto toApi(Timer t) {
        TimerDto api = new TimerDto();
        api.setId(t.getId());
        api.setName(t.getName());
        api.setDescription(t.getDescription());
        api.setCronExpression(t.getCronExpression());
        api.setZoneId(t.getZoneId());
        api.setTriggerTime(t.getTriggerTime() != null ? t.getTriggerTime().toString() : null);
        api.setSuspended(t.isSuspended());
        api.setCountries(t.getCountries());
        api.setRegions(t.getRegions());
        api.setExcludedCountries(t.getExcludedCountries());
        api.setExcludedRegions(t.getExcludedRegions());
        api.setFlowTypes(t.getFlowTypes());
        api.setClientIds(t.getClientIds());
        api.setProductTypes(t.getProductTypes() != null ?
                t.getProductTypes().stream()
                 .map(productType -> TimerDto.ProductTypesEnum.fromValue(productType))
                 .collect(Collectors.toList()) : null);
        return api;
    }

    private void validateGeo(CreateTimerRequestDto req) {
        if (req.getCountries() != null) {
            boolean invalid = req.getCountries().stream().anyMatch(c -> !geoCatalogService.isValidCountryCode(c));
            if (invalid) {
                throw new IllegalArgumentException("Invalid country code in request");
            }
        }
        if (req.getRegions() != null) {
            boolean invalid = req.getRegions().stream().anyMatch(r -> !geoCatalogService.isValidRegionCode(r));
            if (invalid) {
                throw new IllegalArgumentException("Invalid region code in request");
            }
        }
    }

    private Timer fromDto(CreateTimerRequestDto dto, Timer target) {
        validateZoneAndCron(dto);
        target.setName(dto.getName());
        target.setDescription(dto.getDescription());
        target.setCronExpression(dto.getCronExpression());
        target.setZoneId(dto.getZoneId());
        if (dto.getTriggerTime() != null) {
            java.time.LocalTime lt = java.time.LocalTime.parse(dto.getTriggerTime());
            target.setTriggerTime(lt);
        } else {
            target.setTriggerTime(null);
        }
        target.setSuspended(Boolean.TRUE.equals(dto.getSuspended()));
        target.setCountries(dto.getCountries());
        target.setRegions(dto.getRegions());
        target.setSubregions(dto.getSubregions());
        target.setExcludedCountries(dto.getExcludedCountries());
        target.setExcludedRegions(dto.getExcludedRegions());
        target.setFlowTypes(dto.getFlowTypes());
        target.setClientIds(dto.getClientIds());
        target.setProductTypes(dto.getProductTypes() != null ?
                dto.getProductTypes().stream().map(Enum::name).collect(Collectors.toList()) : null);
        return target;
    }

    private void validateZoneAndCron(CreateTimerRequestDto dto) {
        if (dto.getZoneId() == null || dto.getZoneId().isBlank()) {
            throw new IllegalArgumentException("zoneId is required");
        }
        try {
            java.time.ZoneId.of(dto.getZoneId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid zoneId");
        }
        if (dto.getCronExpression() != null && !dto.getCronExpression().isBlank()) {
            try {
                org.quartz.CronExpression.validateExpression(dto.getCronExpression());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid cronExpression");
            }
        }
    }
}
