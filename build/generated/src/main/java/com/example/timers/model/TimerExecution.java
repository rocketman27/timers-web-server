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
 * TimerExecution
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.7.0")
public class TimerExecution implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String instanceId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledFor = null;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startedAt = null;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime finishedAt = null;

  /**
   * Gets or Sets outcome
   */
  public enum OutcomeEnum {
    SUCCESS("SUCCESS"),
    
    FAILED("FAILED"),
    
    SKIPPED("SKIPPED");

    private String value;

    OutcomeEnum(String value) {
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
    public static OutcomeEnum fromValue(String value) {
      for (OutcomeEnum b : OutcomeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  private OutcomeEnum outcome = null;

  private String errorMessage = null;

  /**
   * Gets or Sets triggerType
   */
  public enum TriggerTypeEnum {
    SCHEDULED("SCHEDULED"),
    
    MANUAL("MANUAL");

    private String value;

    TriggerTypeEnum(String value) {
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
    public static TriggerTypeEnum fromValue(String value) {
      for (TriggerTypeEnum b : TriggerTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  private TriggerTypeEnum triggerType = null;

  public TimerExecution() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TimerExecution(String id, String instanceId) {
    this.id = id;
    this.instanceId = instanceId;
  }

  public TimerExecution id(String id) {
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

  public TimerExecution instanceId(String instanceId) {
    this.instanceId = instanceId;
    return this;
  }

  /**
   * Get instanceId
   * @return instanceId
   */
  @NotNull 
  @Schema(name = "instanceId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("instanceId")
  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public TimerExecution scheduledFor(OffsetDateTime scheduledFor) {
    this.scheduledFor = scheduledFor;
    return this;
  }

  /**
   * Get scheduledFor
   * @return scheduledFor
   */
  @Valid 
  @Schema(name = "scheduledFor", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("scheduledFor")
  public OffsetDateTime getScheduledFor() {
    return scheduledFor;
  }

  public void setScheduledFor(OffsetDateTime scheduledFor) {
    this.scheduledFor = scheduledFor;
  }

  public TimerExecution startedAt(OffsetDateTime startedAt) {
    this.startedAt = startedAt;
    return this;
  }

  /**
   * Get startedAt
   * @return startedAt
   */
  @Valid 
  @Schema(name = "startedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startedAt")
  public OffsetDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(OffsetDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public TimerExecution finishedAt(OffsetDateTime finishedAt) {
    this.finishedAt = finishedAt;
    return this;
  }

  /**
   * Get finishedAt
   * @return finishedAt
   */
  @Valid 
  @Schema(name = "finishedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("finishedAt")
  public OffsetDateTime getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(OffsetDateTime finishedAt) {
    this.finishedAt = finishedAt;
  }

  public TimerExecution outcome(OutcomeEnum outcome) {
    this.outcome = outcome;
    return this;
  }

  /**
   * Get outcome
   * @return outcome
   */
  
  @Schema(name = "outcome", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("outcome")
  public OutcomeEnum getOutcome() {
    return outcome;
  }

  public void setOutcome(OutcomeEnum outcome) {
    this.outcome = outcome;
  }

  public TimerExecution errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * Get errorMessage
   * @return errorMessage
   */
  
  @Schema(name = "errorMessage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public TimerExecution triggerType(TriggerTypeEnum triggerType) {
    this.triggerType = triggerType;
    return this;
  }

  /**
   * Get triggerType
   * @return triggerType
   */
  
  @Schema(name = "triggerType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("triggerType")
  public TriggerTypeEnum getTriggerType() {
    return triggerType;
  }

  public void setTriggerType(TriggerTypeEnum triggerType) {
    this.triggerType = triggerType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimerExecution timerExecution = (TimerExecution) o;
    return Objects.equals(this.id, timerExecution.id) &&
        Objects.equals(this.instanceId, timerExecution.instanceId) &&
        Objects.equals(this.scheduledFor, timerExecution.scheduledFor) &&
        Objects.equals(this.startedAt, timerExecution.startedAt) &&
        Objects.equals(this.finishedAt, timerExecution.finishedAt) &&
        Objects.equals(this.outcome, timerExecution.outcome) &&
        Objects.equals(this.errorMessage, timerExecution.errorMessage) &&
        Objects.equals(this.triggerType, timerExecution.triggerType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, instanceId, scheduledFor, startedAt, finishedAt, outcome, errorMessage, triggerType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TimerExecution {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    instanceId: ").append(toIndentedString(instanceId)).append("\n");
    sb.append("    scheduledFor: ").append(toIndentedString(scheduledFor)).append("\n");
    sb.append("    startedAt: ").append(toIndentedString(startedAt)).append("\n");
    sb.append("    finishedAt: ").append(toIndentedString(finishedAt)).append("\n");
    sb.append("    outcome: ").append(toIndentedString(outcome)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    triggerType: ").append(toIndentedString(triggerType)).append("\n");
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

