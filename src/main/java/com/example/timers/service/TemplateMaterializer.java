package com.example.timers.service;

import com.example.timers.domain.TimerStatus;
import com.example.timers.domain.instance.TimerInstance;
import com.example.timers.domain.template.TimerTemplate;
import com.example.timers.repository.TimerInstanceRepository;
import com.example.timers.scheduler.TimerScheduler;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class TemplateMaterializer {

    private final TimerInstanceRepository instanceRepository;
    private final TimerScheduler scheduler;

    public TemplateMaterializer(TimerInstanceRepository instanceRepository, TimerScheduler scheduler) {
        this.instanceRepository = instanceRepository;
        this.scheduler = scheduler;
    }

    public List<TimerInstance> materializeAndSchedule(TimerTemplate template) {
        List<String> countries = nonEmpty(template.getCountries());
        List<String> regions = nonEmpty(template.getRegions());
        List<String> subregions = nonEmpty(template.getSubregions());
        List<String> flowTypes = nonEmpty(template.getFlowTypes());
        List<String> clientIds = nonEmpty(template.getClientIds());
        List<String> productTypes = nonEmpty(template.getProductTypes());

        List<TimerInstance> instances = countries.stream()
                .flatMap(country -> regions.stream().flatMap(region ->
                        subregions.stream().flatMap(subregion ->
                                flowTypes.stream().flatMap(flowType ->
                                        clientIds.stream().flatMap(clientId ->
                                                productTypes.stream().map(productType ->
                                        buildInstance(template, country, region, subregion, flowType, clientId, productType)
                                ))))))
                .toList();

        // Upsert (saveAll will insert/update by id), then schedule each
        List<TimerInstance> saved = instanceRepository.saveAll(instances);
        String cron = resolveCron(template);
        for (TimerInstance inst : saved) {
            try {
                scheduler.scheduleOrUpdate(
                    inst.getId(), 
                    cron, 
                    template.getZoneId(),
                    template.getName(),
                    inst.getCountry(),
                    inst.getRegion(),
                    inst.getSubregion(),
                    inst.getFlowType(),
                    inst.getClientId(),
                    inst.getProductType(),
                    template.getExcludedCountries(),
                    template.getExcludedRegions()
                );
            } catch (Exception ignored) {}
        }
        return saved;
    }

    private static String resolveCron(TimerTemplate template) {
        if (template.getCronExpression() != null && !template.getCronExpression().isBlank()) {
            return template.getCronExpression();
        }
        if (template.getTriggerTime() != null) {
            int hh = template.getTriggerTime().getHour();
            int mm = template.getTriggerTime().getMinute();
            return String.format("0 %d %d * * ?", mm, hh);
        }
        // default: midnight
        return "0 0 0 * * ?";
    }

    private TimerInstance buildInstance(TimerTemplate template,
                                        String country,
                                        String region,
                                        String subregion,
                                        String flowType,
                                        String clientId,
                                        String productType) {
        TimerInstance inst = new TimerInstance();
        inst.setTemplateId(template.getId());
        inst.setCountry(country);
        inst.setRegion(region);
        inst.setSubregion(subregion);
        inst.setFlowType(flowType);
        inst.setClientId(clientId);
        inst.setProductType(productType);
        inst.setZoneId(template.getZoneId());
        inst.setExcludedCountries(template.getExcludedCountries());
        inst.setExcludedRegions(template.getExcludedRegions());
        // start/end window removed; no effective bounds in daily trigger model
        inst.setStatus(template.isSuspended() ? TimerStatus.SUSPENDED : TimerStatus.ACTIVE);
        inst.setId(deterministicId(template.getId(), country, region, subregion, flowType, clientId, productType, template.getExcludedCountries(), template.getExcludedRegions()));
        return inst;
    }

    private static List<String> nonEmpty(List<String> list) {
        if (list == null || list.isEmpty()) return List.of("");
        return list;
    }

    private static String deterministicId(String templateId, String country, String region, String subregion, String flowType, String clientId, String productType, List<String> excludedCountries, List<String> excludedRegions) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(templateId.getBytes(StandardCharsets.UTF_8));
            md.update(("|" + (country == null ? "" : country)).getBytes(StandardCharsets.UTF_8));
            md.update(("|" + (region == null ? "" : region)).getBytes(StandardCharsets.UTF_8));
            md.update(("|" + (subregion == null ? "" : subregion)).getBytes(StandardCharsets.UTF_8));
            md.update(("|" + (flowType == null ? "" : flowType)).getBytes(StandardCharsets.UTF_8));
            md.update(("|" + (clientId == null ? "" : clientId)).getBytes(StandardCharsets.UTF_8));
            md.update(("|" + (productType == null ? "" : productType)).getBytes(StandardCharsets.UTF_8));
            
            // Include exclusion information in the ID
            String excludedCountriesStr = excludedCountries != null ? String.join(",", excludedCountries) : "";
            String excludedRegionsStr = excludedRegions != null ? String.join(",", excludedRegions) : "";
            md.update(("|" + excludedCountriesStr).getBytes(StandardCharsets.UTF_8));
            md.update(("|" + excludedRegionsStr).getBytes(StandardCharsets.UTF_8));
            
            byte[] hash = md.digest();
            // short, URL-safe-ish
            return Base64.getUrlEncoder().withoutPadding().encodeToString(Arrays.copyOf(hash, 16));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}


