package com.example.timers.domain.event;

import java.time.OffsetDateTime;
import java.util.List;

public class TimerExecutionEvent {
    private String instanceId;
    private String templateId;
    private String templateName;
    private String country;
    private String region;
    private String subregion;
    private String flowType;
    private String clientId;
    private String productType;
    private List<String> excludedCountries;
    private List<String> excludedRegions;
    private String zoneId;
    private OffsetDateTime executedAt;
    private String triggerType;

    public TimerExecutionEvent() { }

    public TimerExecutionEvent(String instanceId,
                               String templateId,
                               String templateName,
                               String country,
                               String region,
                               String subregion,
                               String flowType,
                               String clientId,
                               String productType,
                               List<String> excludedCountries,
                               List<String> excludedRegions,
                               String zoneId,
                               OffsetDateTime executedAt,
                               String triggerType) {
        this.instanceId = instanceId;
        this.templateId = templateId;
        this.templateName = templateName;
        this.country = country;
        this.region = region;
        this.subregion = subregion;
        this.flowType = flowType;
        this.clientId = clientId;
        this.productType = productType;
        this.excludedCountries = excludedCountries;
        this.excludedRegions = excludedRegions;
        this.zoneId = zoneId;
        this.executedAt = executedAt;
        this.triggerType = triggerType;
    }

    public String getInstanceId() { return instanceId; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
    public String getTemplateId() { return templateId; }
    public void setTemplateId(String templateId) { this.templateId = templateId; }
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getSubregion() { return subregion; }
    public void setSubregion(String subregion) { this.subregion = subregion; }
    public String getFlowType() { return flowType; }
    public void setFlowType(String flowType) { this.flowType = flowType; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    public List<String> getExcludedCountries() { return excludedCountries; }
    public void setExcludedCountries(List<String> excludedCountries) { this.excludedCountries = excludedCountries; }
    public List<String> getExcludedRegions() { return excludedRegions; }
    public void setExcludedRegions(List<String> excludedRegions) { this.excludedRegions = excludedRegions; }
    public String getZoneId() { return zoneId; }
    public void setZoneId(String zoneId) { this.zoneId = zoneId; }
    public OffsetDateTime getExecutedAt() { return executedAt; }
    public void setExecutedAt(OffsetDateTime executedAt) { this.executedAt = executedAt; }
    public String getTriggerType() { return triggerType; }
    public void setTriggerType(String triggerType) { this.triggerType = triggerType; }
}



