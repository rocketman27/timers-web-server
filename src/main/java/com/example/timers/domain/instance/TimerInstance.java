package com.example.timers.domain.instance;

import com.example.timers.domain.TimerStatus;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "timer_instances")
public class TimerInstance {
    @Id
    private String id; // deterministic from template + dims

    private String templateId;

    private String country;
    private String region;
    private String subregion;
    private String flowType;
    private String clientId;
    private String productType;

    @Enumerated(EnumType.STRING)
    private TimerStatus status = TimerStatus.ACTIVE;

    private String zoneId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_instance_excluded_countries", joinColumns = @JoinColumn(name = "instance_id"))
    @Column(name = "country")
    private List<String> excludedCountries = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_instance_excluded_regions", joinColumns = @JoinColumn(name = "instance_id"))
    @Column(name = "region")
    private List<String> excludedRegions = new ArrayList<>();

    private OffsetDateTime lastSuccessAt;
    private OffsetDateTime lastAttemptAt;
    private Integer attemptCountToday = 0;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTemplateId() { return templateId; }
    public void setTemplateId(String templateId) { this.templateId = templateId; }
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
    public TimerStatus getStatus() { return status; }
    public void setStatus(TimerStatus status) { this.status = status; }
    public String getZoneId() { return zoneId; }
    public void setZoneId(String zoneId) { this.zoneId = zoneId; }
    public List<String> getExcludedCountries() { return excludedCountries; }
    public void setExcludedCountries(List<String> excludedCountries) { this.excludedCountries = excludedCountries; }
    public List<String> getExcludedRegions() { return excludedRegions; }
    public void setExcludedRegions(List<String> excludedRegions) { this.excludedRegions = excludedRegions; }
    public OffsetDateTime getLastSuccessAt() { return lastSuccessAt; }
    public void setLastSuccessAt(OffsetDateTime lastSuccessAt) { this.lastSuccessAt = lastSuccessAt; }
    public OffsetDateTime getLastAttemptAt() { return lastAttemptAt; }
    public void setLastAttemptAt(OffsetDateTime lastAttemptAt) { this.lastAttemptAt = lastAttemptAt; }
    public Integer getAttemptCountToday() { return attemptCountToday; }
    public void setAttemptCountToday(Integer attemptCountToday) { this.attemptCountToday = attemptCountToday; }
}


