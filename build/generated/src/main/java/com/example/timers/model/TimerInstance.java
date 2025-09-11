package com.example.timers.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TimerInstance
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class TimerInstance implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String templateId;

  private String country;

  private String region;

  private String subregion;

  private String flowType;

  private String clientId;

  /**
   * Gets or Sets productType
   */
  public enum ProductTypeEnum {
    CASH("CASH"),
    
    SWAP("SWAP");

    private String value;

    ProductTypeEnum(String value) {
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
    public static ProductTypeEnum fromValue(String value) {
      for (ProductTypeEnum b : ProductTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ProductTypeEnum productType;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    ACTIVE("ACTIVE"),
    
    SUSPENDED("SUSPENDED"),
    
    RUNNING("RUNNING"),
    
    COMPLETED("COMPLETED"),
    
    FAILED("FAILED");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  private String zoneId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime effectiveStartAt = null;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime effectiveEndAt = null;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime lastSuccessAt = null;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime lastAttemptAt = null;

  private Integer attemptCountToday = null;

  public TimerInstance() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TimerInstance(String id, String templateId, String country, String region, String subregion, String flowType, String clientId, ProductTypeEnum productType, StatusEnum status, String zoneId) {
    this.id = id;
    this.templateId = templateId;
    this.country = country;
    this.region = region;
    this.subregion = subregion;
    this.flowType = flowType;
    this.clientId = clientId;
    this.productType = productType;
    this.status = status;
    this.zoneId = zoneId;
  }

  public TimerInstance id(String id) {
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

  public TimerInstance templateId(String templateId) {
    this.templateId = templateId;
    return this;
  }

  /**
   * Get templateId
   * @return templateId
   */
  @NotNull 
  @Schema(name = "templateId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("templateId")
  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public TimerInstance country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   */
  @NotNull 
  @Schema(name = "country", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public TimerInstance region(String region) {
    this.region = region;
    return this;
  }

  /**
   * Get region
   * @return region
   */
  @NotNull 
  @Schema(name = "region", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("region")
  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public TimerInstance subregion(String subregion) {
    this.subregion = subregion;
    return this;
  }

  /**
   * Get subregion
   * @return subregion
   */
  @NotNull 
  @Schema(name = "subregion", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("subregion")
  public String getSubregion() {
    return subregion;
  }

  public void setSubregion(String subregion) {
    this.subregion = subregion;
  }

  public TimerInstance flowType(String flowType) {
    this.flowType = flowType;
    return this;
  }

  /**
   * Get flowType
   * @return flowType
   */
  @NotNull 
  @Schema(name = "flowType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flowType")
  public String getFlowType() {
    return flowType;
  }

  public void setFlowType(String flowType) {
    this.flowType = flowType;
  }

  public TimerInstance clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Get clientId
   * @return clientId
   */
  @NotNull 
  @Schema(name = "clientId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("clientId")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public TimerInstance productType(ProductTypeEnum productType) {
    this.productType = productType;
    return this;
  }

  /**
   * Get productType
   * @return productType
   */
  @NotNull 
  @Schema(name = "productType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("productType")
  public ProductTypeEnum getProductType() {
    return productType;
  }

  public void setProductType(ProductTypeEnum productType) {
    this.productType = productType;
  }

  public TimerInstance status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @NotNull 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public TimerInstance zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  /**
   * Get zoneId
   * @return zoneId
   */
  @NotNull 
  @Schema(name = "zoneId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("zoneId")
  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public TimerInstance effectiveStartAt(OffsetDateTime effectiveStartAt) {
    this.effectiveStartAt = effectiveStartAt;
    return this;
  }

  /**
   * Get effectiveStartAt
   * @return effectiveStartAt
   */
  @Valid 
  @Schema(name = "effectiveStartAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("effectiveStartAt")
  public OffsetDateTime getEffectiveStartAt() {
    return effectiveStartAt;
  }

  public void setEffectiveStartAt(OffsetDateTime effectiveStartAt) {
    this.effectiveStartAt = effectiveStartAt;
  }

  public TimerInstance effectiveEndAt(OffsetDateTime effectiveEndAt) {
    this.effectiveEndAt = effectiveEndAt;
    return this;
  }

  /**
   * Get effectiveEndAt
   * @return effectiveEndAt
   */
  @Valid 
  @Schema(name = "effectiveEndAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("effectiveEndAt")
  public OffsetDateTime getEffectiveEndAt() {
    return effectiveEndAt;
  }

  public void setEffectiveEndAt(OffsetDateTime effectiveEndAt) {
    this.effectiveEndAt = effectiveEndAt;
  }

  public TimerInstance lastSuccessAt(OffsetDateTime lastSuccessAt) {
    this.lastSuccessAt = lastSuccessAt;
    return this;
  }

  /**
   * Get lastSuccessAt
   * @return lastSuccessAt
   */
  @Valid 
  @Schema(name = "lastSuccessAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastSuccessAt")
  public OffsetDateTime getLastSuccessAt() {
    return lastSuccessAt;
  }

  public void setLastSuccessAt(OffsetDateTime lastSuccessAt) {
    this.lastSuccessAt = lastSuccessAt;
  }

  public TimerInstance lastAttemptAt(OffsetDateTime lastAttemptAt) {
    this.lastAttemptAt = lastAttemptAt;
    return this;
  }

  /**
   * Get lastAttemptAt
   * @return lastAttemptAt
   */
  @Valid 
  @Schema(name = "lastAttemptAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastAttemptAt")
  public OffsetDateTime getLastAttemptAt() {
    return lastAttemptAt;
  }

  public void setLastAttemptAt(OffsetDateTime lastAttemptAt) {
    this.lastAttemptAt = lastAttemptAt;
  }

  public TimerInstance attemptCountToday(Integer attemptCountToday) {
    this.attemptCountToday = attemptCountToday;
    return this;
  }

  /**
   * Get attemptCountToday
   * @return attemptCountToday
   */
  
  @Schema(name = "attemptCountToday", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("attemptCountToday")
  public Integer getAttemptCountToday() {
    return attemptCountToday;
  }

  public void setAttemptCountToday(Integer attemptCountToday) {
    this.attemptCountToday = attemptCountToday;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimerInstance timerInstance = (TimerInstance) o;
    return Objects.equals(this.id, timerInstance.id) &&
        Objects.equals(this.templateId, timerInstance.templateId) &&
        Objects.equals(this.country, timerInstance.country) &&
        Objects.equals(this.region, timerInstance.region) &&
        Objects.equals(this.subregion, timerInstance.subregion) &&
        Objects.equals(this.flowType, timerInstance.flowType) &&
        Objects.equals(this.clientId, timerInstance.clientId) &&
        Objects.equals(this.productType, timerInstance.productType) &&
        Objects.equals(this.status, timerInstance.status) &&
        Objects.equals(this.zoneId, timerInstance.zoneId) &&
        Objects.equals(this.effectiveStartAt, timerInstance.effectiveStartAt) &&
        Objects.equals(this.effectiveEndAt, timerInstance.effectiveEndAt) &&
        Objects.equals(this.lastSuccessAt, timerInstance.lastSuccessAt) &&
        Objects.equals(this.lastAttemptAt, timerInstance.lastAttemptAt) &&
        Objects.equals(this.attemptCountToday, timerInstance.attemptCountToday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, templateId, country, region, subregion, flowType, clientId, productType, status, zoneId, effectiveStartAt, effectiveEndAt, lastSuccessAt, lastAttemptAt, attemptCountToday);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimerInstance {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    templateId: ").append(toIndentedString(templateId)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    subregion: ").append(toIndentedString(subregion)).append("\n");
    sb.append("    flowType: ").append(toIndentedString(flowType)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
    sb.append("    effectiveStartAt: ").append(toIndentedString(effectiveStartAt)).append("\n");
    sb.append("    effectiveEndAt: ").append(toIndentedString(effectiveEndAt)).append("\n");
    sb.append("    lastSuccessAt: ").append(toIndentedString(lastSuccessAt)).append("\n");
    sb.append("    lastAttemptAt: ").append(toIndentedString(lastAttemptAt)).append("\n");
    sb.append("    attemptCountToday: ").append(toIndentedString(attemptCountToday)).append("\n");
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

