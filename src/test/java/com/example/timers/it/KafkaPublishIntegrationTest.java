package com.example.timers.it;

import com.example.timers.it.support.TimerRequests;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.http.MediaType;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@EmbeddedKafka(partitions = 1, topics = {"timer-executions"})
public class KafkaPublishIntegrationTest extends BaseIntegrationTest {

    @Value("${app.kafka.topic.timer-executions:timer-executions}")
    String topic;

    @Autowired
    EmbeddedKafkaBroker embeddedKafka;

    @Test
    void manualTrigger_publishesKafkaEvent() throws Exception {
        String id = mockMvc.perform(post("/api/templates").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.create("k1", "UTC", "10:15", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(post("/api/timers/" + id + "/_trigger")).andExpect(status().isNoContent());

        Map<String, Object> consumerProps = new HashMap<>(KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka));
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        var consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), new JsonDeserializer<>(Object.class, false));
        var consumer = consumerFactory.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic);

        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            ConsumerRecord<String, Object> rec = KafkaTestUtils.getSingleRecord(consumer, topic, Duration.ofSeconds(3));
            assertThat(rec).isNotNull();
        });
        consumer.close();
    }

    @Test
    void scheduledTrigger_publishesKafkaEvent() throws Exception {
        String id = mockMvc.perform(post("/api/templates").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.createCron("k2", "UTC", "0/2 * * * * ?", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        Map<String, Object> consumerProps = new HashMap<>(KafkaTestUtils.consumerProps("testGroup2", "true", embeddedKafka));
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        var consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), new JsonDeserializer<>(Object.class, false));
        var consumer = consumerFactory.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic);

        await().atMost(Duration.ofSeconds(12)).untilAsserted(() -> {
            ConsumerRecord<String, Object> rec = KafkaTestUtils.getSingleRecord(consumer, topic, Duration.ofSeconds(5));
            assertThat(rec).isNotNull();
        });
        consumer.close();
    }
}



