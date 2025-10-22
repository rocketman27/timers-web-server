package com.bnpparibas.tr.timer.domain.execution;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "timer_executions")
public class TimerExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String instanceId;
    private String timerId;
    private OffsetDateTime scheduledFor;
    private OffsetDateTime startedAt;
    private OffsetDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    private TimerExecutionOutcome outcome;

    private String errorMessage;

    @Enumerated(EnumType.STRING)
    private TriggerType triggerType;

    private String idempotencyKey;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getInstanceId() { return instanceId; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
    public String getTimerId() { return timerId; }
    public void setTimerId(String timerId) { this.timerId = timerId; }
    public OffsetDateTime getScheduledFor() { return scheduledFor; }
    public void setScheduledFor(OffsetDateTime scheduledFor) { this.scheduledFor = scheduledFor; }
    public OffsetDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(OffsetDateTime startedAt) { this.startedAt = startedAt; }
    public OffsetDateTime getFinishedAt() { return finishedAt; }
    public void setFinishedAt(OffsetDateTime finishedAt) { this.finishedAt = finishedAt; }
    public TimerExecutionOutcome getOutcome() { return outcome; }
    public void setOutcome(TimerExecutionOutcome outcome) { this.outcome = outcome; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public TriggerType getTriggerType() { return triggerType; }
    public void setTriggerType(TriggerType triggerType) { this.triggerType = triggerType; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}


