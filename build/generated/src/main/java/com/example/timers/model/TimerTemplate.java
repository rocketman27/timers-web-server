package com.example.timers.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TimerTemplate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class TimerTemplate implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String name;

  private String description = null;

  private String cronExpression = null;

  private String zoneId;

  private String triggerTime;

  private Boolean suspended = false;

  @Valid
  private List<String> countries = new ArrayList<>();

  @Valid
  private List<String> regions = new ArrayList<>();

  @Valid
  private List<String> subregions = new ArrayList<>();

  @Valid
  private List<String> flowTypes = new ArrayList<>();

  @Valid
  private List<String> clientIds = new ArrayList<>();

  /**
   * Gets or Sets productTypes
   */
  public enum ProductTypesEnum {
    CASH("CASH"),
    
    SWAP("SWAP");

    private String value;

    ProductTypesEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ProductTypesEnum fromValue(String value) {
      for (ProductTypesEnum b : ProductTypesEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @Valid
  private List<ProductTypesEnum> productTypes = new ArrayList<>();

  public TimerTemplate() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TimerTemplate(String id, String name, String zoneId) {
    this.id = id;
    this.name = name;
    this.zoneId = zoneId;
  }

  public TimerTemplate id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TimerTemplate name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TimerTemplate description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TimerTemplate cronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
    return this;
  }

  /**
   * Optional. If omitted, backend derives a daily cron from triggerTime.
   * @return cronExpression
   */
  
  @Schema(name = "cronExpression", description = "Optional. If omitted, backend derives a daily cron from triggerTime.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cronExpression")
  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public TimerTemplate zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  /**
   * IANA time zone id
   * @return zoneId
   */
  @NotNull 
  @Schema(name = "zoneId", example = "Europe/London", description = "IANA time zone id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("zoneId")
  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public TimerTemplate triggerTime(String triggerTime) {
    this.triggerTime = triggerTime;
    return this;
  }

  /**
   * Daily trigger time HH:mm
   * @return triggerTime
   */
  
  @Schema(name = "triggerTime", example = "615", description = "Daily trigger time HH:mm", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("triggerTime")
  public String getTriggerTime() {
    return triggerTime;
  }

  public void setTriggerTime(String triggerTime) {
    this.triggerTime = triggerTime;
  }

  public TimerTemplate suspended(Boolean suspended) {
    this.suspended = suspended;
    return this;
  }

  /**
   * Get suspended
   * @return suspended
   */
  
  @Schema(name = "suspended", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("suspended")
  public Boolean getSuspended() {
    return suspended;
  }

  public void setSuspended(Boolean suspended) {
    this.suspended = suspended;
  }

  public TimerTemplate countries(List<String> countries) {
    this.countries = countries;
    return this;
  }

  public TimerTemplate addCountriesItem(String countriesItem) {
    if (this.countries == null) {
      this.countries = new ArrayList<>();
    }
    this.countries.add(countriesItem);
    return this;
  }

  /**
   * Get countries
   * @return countries
   */
  
  @Schema(name = "countries", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("countries")
  public List<String> getCountries() {
    return countries;
  }

  public void setCountries(List<String> countries) {
    this.countries = countries;
  }

  public TimerTemplate regions(List<String> regions) {
    this.regions = regions;
    return this;
  }

  public TimerTemplate addRegionsItem(String regionsItem) {
    if (this.regions == null) {
      this.regions = new ArrayList<>();
    }
    this.regions.add(regionsItem);
    return this;
  }

  /**
   * Get regions
   * @return regions
   */
  
  @Schema(name = "regions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("regions")
  public List<String> getRegions() {
    return regions;
  }

  public void setRegions(List<String> regions) {
    this.regions = regions;
  }

  public TimerTemplate subregions(List<String> subregions) {
    this.subregions = subregions;
    return this;
  }

  public TimerTemplate addSubregionsItem(String subregionsItem) {
    if (this.subregions == null) {
      this.subregions = new ArrayList<>();
    }
    this.subregions.add(subregionsItem);
    return this;
  }

  /**
   * Get subregions
   * @return subregions
   */
  
  @Schema(name = "subregions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subregions")
  public List<String> getSubregions() {
    return subregions;
  }

  public void setSubregions(List<String> subregions) {
    this.subregions = subregions;
  }

  public TimerTemplate flowTypes(List<String> flowTypes) {
    this.flowTypes = flowTypes;
    return this;
  }

  public TimerTemplate addFlowTypesItem(String flowTypesItem) {
    if (this.flowTypes == null) {
      this.flowTypes = new ArrayList<>();
    }
    this.flowTypes.add(flowTypesItem);
    return this;
  }

  /**
   * Get flowTypes
   * @return flowTypes
   */
  
  @Schema(name = "flowTypes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flowTypes")
  public List<String> getFlowTypes() {
    return flowTypes;
  }

  public void setFlowTypes(List<String> flowTypes) {
    this.flowTypes = flowTypes;
  }

  public TimerTemplate clientIds(List<String> clientIds) {
    this.clientIds = clientIds;
    return this;
  }

  public TimerTemplate addClientIdsItem(String clientIdsItem) {
    if (this.clientIds == null) {
      this.clientIds = new ArrayList<>();
    }
    this.clientIds.add(clientIdsItem);
    return this;
  }

  /**
   * Get clientIds
   * @return clientIds
   */
  
  @Schema(name = "clientIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("clientIds")
  public List<String> getClientIds() {
    return clientIds;
  }

  public void setClientIds(List<String> clientIds) {
    this.clientIds = clientIds;
  }

  public TimerTemplate productTypes(List<ProductTypesEnum> productTypes) {
    this.productTypes = productTypes;
    return this;
  }

  public TimerTemplate addProductTypesItem(ProductTypesEnum productTypesItem) {
    if (this.productTypes == null) {
      this.productTypes = new ArrayList<>();
    }
    this.productTypes.add(productTypesItem);
    return this;
  }

  /**
   * Get productTypes
   * @return productTypes
   */
  
  @Schema(name = "productTypes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("productTypes")
  public List<ProductTypesEnum> getProductTypes() {
    return productTypes;
  }

  public void setProductTypes(List<ProductTypesEnum> productTypes) {
    this.productTypes = productTypes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimerTemplate timerTemplate = (TimerTemplate) o;
    return Objects.equals(this.id, timerTemplate.id) &&
        Objects.equals(this.name, timerTemplate.name) &&
        Objects.equals(this.description, timerTemplate.description) &&
        Objects.equals(this.cronExpression, timerTemplate.cronExpression) &&
        Objects.equals(this.zoneId, timerTemplate.zoneId) &&
        Objects.equals(this.triggerTime, timerTemplate.triggerTime) &&
        Objects.equals(this.suspended, timerTemplate.suspended) &&
        Objects.equals(this.countries, timerTemplate.countries) &&
        Objects.equals(this.regions, timerTemplate.regions) &&
        Objects.equals(this.subregions, timerTemplate.subregions) &&
        Objects.equals(this.flowTypes, timerTemplate.flowTypes) &&
        Objects.equals(this.clientIds, timerTemplate.clientIds) &&
        Objects.equals(this.productTypes, timerTemplate.productTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, cronExpression, zoneId, triggerTime, suspended, countries, regions, subregions, flowTypes, clientIds, productTypes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimerTemplate {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    cronExpression: ").append(toIndentedString(cronExpression)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
    sb.append("    triggerTime: ").append(toIndentedString(triggerTime)).append("\n");
    sb.append("    suspended: ").append(toIndentedString(suspended)).append("\n");
    sb.append("    countries: ").append(toIndentedString(countries)).append("\n");
    sb.append("    regions: ").append(toIndentedString(regions)).append("\n");
    sb.append("    subregions: ").append(toIndentedString(subregions)).append("\n");
    sb.append("    flowTypes: ").append(toIndentedString(flowTypes)).append("\n");
    sb.append("    clientIds: ").append(toIndentedString(clientIds)).append("\n");
    sb.append("    productTypes: ").append(toIndentedString(productTypes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

