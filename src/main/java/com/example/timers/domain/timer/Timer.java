package com.example.timers.domain.timer;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "timers")
public class Timer {

    @Id
    private String id;

    private String name;
    private String description;

    private String cronExpression;
    private String zoneId;
    private LocalTime triggerTime;
    private boolean suspended = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_countries", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "country")
    private List<String> countries = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_regions", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "region")
    private List<String> regions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_subregions", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "subregion")
    private List<String> subregions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_flow_types", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "flow_type")
    private List<String> flowTypes = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_client_ids", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "client_id")
    private List<String> clientIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_product_types", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "product_type")
    private List<String> productTypes = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_excluded_countries", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "excluded_country")
    private List<String> excludedCountries = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "timer_excluded_regions", joinColumns = @JoinColumn(name = "timer_id"))
    @Column(name = "excluded_region")
    private List<String> excludedRegions = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCronExpression() { return cronExpression; }
    public void setCronExpression(String cronExpression) { this.cronExpression = cronExpression; }
    public String getZoneId() { return zoneId; }
    public void setZoneId(String zoneId) { this.zoneId = zoneId; }
    public LocalTime getTriggerTime() { return triggerTime; }
    public void setTriggerTime(LocalTime triggerTime) { this.triggerTime = triggerTime; }
    public boolean isSuspended() { return suspended; }
    public void setSuspended(boolean suspended) { this.suspended = suspended; }
    public List<String> getCountries() { return countries; }
    public void setCountries(List<String> countries) { this.countries = countries; }
    public List<String> getRegions() { return regions; }
    public void setRegions(List<String> regions) { this.regions = regions; }
    public List<String> getSubregions() { return subregions; }
    public void setSubregions(List<String> subregions) { this.subregions = subregions; }
    public List<String> getFlowTypes() { return flowTypes; }
    public void setFlowTypes(List<String> flowTypes) { this.flowTypes = flowTypes; }
    public List<String> getClientIds() { return clientIds; }
    public void setClientIds(List<String> clientIds) { this.clientIds = clientIds; }
    public List<String> getProductTypes() { return productTypes; }
    public void setProductTypes(List<String> productTypes) { this.productTypes = productTypes; }
    public List<String> getExcludedCountries() { return excludedCountries; }
    public void setExcludedCountries(List<String> excludedCountries) { this.excludedCountries = excludedCountries; }
    public List<String> getExcludedRegions() { return excludedRegions; }
    public void setExcludedRegions(List<String> excludedRegions) { this.excludedRegions = excludedRegions; }
}



