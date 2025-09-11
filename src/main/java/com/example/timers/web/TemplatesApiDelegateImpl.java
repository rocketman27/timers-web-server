package com.example.timers.web;

import com.example.timers.api.TemplatesApiDelegate;
import com.example.timers.model.CreateTemplateRequest;
import com.example.timers.model.TimerTemplate;
import com.example.timers.service.TemplateService;
import com.example.timers.service.GeoCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplatesApiDelegateImpl implements TemplatesApiDelegate {

    private final TemplateService templateService;
    private final GeoCatalogService geoCatalogService;

    public TemplatesApiDelegateImpl(TemplateService templateService, GeoCatalogService geoCatalogService) {
        this.templateService = templateService;
        this.geoCatalogService = geoCatalogService;
    }

    @Override
    public ResponseEntity<TimerTemplate> createTemplate(CreateTemplateRequest createTemplateRequest) {
        validateGeo(createTemplateRequest);
        com.example.timers.domain.template.TimerTemplate t = new com.example.timers.domain.template.TimerTemplate();
        t.setName(createTemplateRequest.getName());
        t.setDescription(createTemplateRequest.getDescription());
        t.setCronExpression(createTemplateRequest.getCronExpression());
        t.setZoneId(createTemplateRequest.getZoneId());
        if (createTemplateRequest.getTriggerTime() != null) {
            java.time.LocalTime lt = java.time.LocalTime.parse(createTemplateRequest.getTriggerTime());
            t.setTriggerTime(lt);
        }
        t.setSuspended(Boolean.TRUE.equals(createTemplateRequest.getSuspended()));
        t.setCountries(createTemplateRequest.getCountries());
        t.setRegions(createTemplateRequest.getRegions());
        t.setSubregions(createTemplateRequest.getSubregions());
        t.setFlowTypes(createTemplateRequest.getFlowTypes());
        t.setClientIds(createTemplateRequest.getClientIds());
        t.setProductTypes(createTemplateRequest.getProductTypes() != null ? 
            createTemplateRequest.getProductTypes().stream()
                .map(Enum::name)
                .collect(Collectors.toList()) : null);

        com.example.timers.domain.template.TimerTemplate saved = templateService.createAndMaterialize(t);
        return ResponseEntity.status(201).body(toApi(saved));
    }

    @Override
    public ResponseEntity<TimerTemplate> getTemplateById(String id) {
        return templateService.get(id)
                .map(this::toApi)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<TimerTemplate>> listTemplates() {
        return ResponseEntity.ok(templateService.list().stream().map(this::toApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TimerTemplate> updateTemplate(String id, CreateTemplateRequest createTemplateRequest) {
        return templateService.get(id)
                .map(existing -> {
                    validateGeo(createTemplateRequest);
                    // Apply updates from request
                    existing.setName(createTemplateRequest.getName());
                    existing.setDescription(createTemplateRequest.getDescription());
                    existing.setCronExpression(createTemplateRequest.getCronExpression());
                    existing.setZoneId(createTemplateRequest.getZoneId());
                    if (createTemplateRequest.getTriggerTime() != null) {
                        java.time.LocalTime lt = java.time.LocalTime.parse(createTemplateRequest.getTriggerTime());
                        existing.setTriggerTime(lt);
                    } else {
                        existing.setTriggerTime(null);
                    }
                    existing.setSuspended(Boolean.TRUE.equals(createTemplateRequest.getSuspended()));
                    existing.setCountries(createTemplateRequest.getCountries());
                    existing.setRegions(createTemplateRequest.getRegions());

                            existing.setFlowTypes(createTemplateRequest.getFlowTypes());
        existing.setClientIds(createTemplateRequest.getClientIds());
        existing.setProductTypes(createTemplateRequest.getProductTypes() != null ? 
            createTemplateRequest.getProductTypes().stream()
                .map(Enum::name)
                .collect(Collectors.toList()) : null);

                    com.example.timers.domain.template.TimerTemplate saved = templateService.updateAndRematerialize(existing);
                    return ResponseEntity.ok(toApi(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private TimerTemplate toApi(com.example.timers.domain.template.TimerTemplate t) {
        TimerTemplate api = new TimerTemplate();
        api.setId(t.getId());
        api.setName(t.getName());
        api.setDescription(t.getDescription());
        api.setCronExpression(t.getCronExpression());
        api.setZoneId(t.getZoneId());
        api.setTriggerTime(t.getTriggerTime() != null ? t.getTriggerTime().toString() : null);
        api.setSuspended(t.isSuspended());
        api.setCountries(t.getCountries());
        api.setRegions(t.getRegions());

        api.setFlowTypes(t.getFlowTypes());
        api.setClientIds(t.getClientIds());
        api.setProductTypes(t.getProductTypes() != null ? 
            t.getProductTypes().stream()
                .map(productType -> TimerTemplate.ProductTypesEnum.fromValue(productType))
                .collect(Collectors.toList()) : null);
        return api;
    }

    private void validateGeo(CreateTemplateRequest req) {
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
}


