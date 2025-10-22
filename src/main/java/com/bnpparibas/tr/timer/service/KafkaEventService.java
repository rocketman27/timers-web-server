package com.bnpparibas.tr.timer.service;

import com.bnpparibas.tr.timer.domain.event.TimerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventService {
    private static final Logger log = LoggerFactory.getLogger(KafkaEventService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;
    private final boolean publishEnabled;

    public KafkaEventService(KafkaTemplate<String, Object> kafkaTemplate,
                             @Value("${app.kafka.topic.timer-executions:timer-executions}") String topic,
                             @Value("${app.kafka.publish-enabled:true}") boolean publishEnabled) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.publishEnabled = publishEnabled;
    }

    public void publishTimerExecution(TimerEvent event) {
        try {
            if (!publishEnabled) {
                log.debug("Kafka publishing disabled; skipping event send");
                return;
            }
            kafkaTemplate.send(topic, event.timerName(), event);
            log.debug("Published TimerExecutionEvent to topic {}: {}", topic, event);
        } catch (Exception e) {
            log.error("Failed to publish TimerExecutionEvent", e);
        }
    }
}


