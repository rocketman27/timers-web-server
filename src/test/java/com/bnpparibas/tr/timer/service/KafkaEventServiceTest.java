package com.bnpparibas.tr.timer.service;

import com.bnpparibas.tr.timer.domain.event.FilterCondition;
import com.bnpparibas.tr.timer.domain.event.TimerEvent;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class KafkaEventServiceTest {

    @Test
    void publishTimerExecution_sendsWhenEnabled() {
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) mock(KafkaTemplate.class);

        KafkaEventService svc = new KafkaEventService(template, "topicA", true);
        TimerEvent evt = new TimerEvent("T1", "default", new FilterCondition());

        svc.publishTimerExecution(evt);

        verify(template, times(1)).send("topicA", "T1", evt);
    }

    @Test
    void publishTimerExecution_noopWhenDisabled() {
        @SuppressWarnings("unchecked")
        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) mock(KafkaTemplate.class);

        KafkaEventService svc = new KafkaEventService(template, "topicA", false);
        TimerEvent evt = new TimerEvent("T1", "default", new FilterCondition());

        svc.publishTimerExecution(evt);

        verify(template, never()).send(any(), any(), any());
    }
}


