package com.bnpparibas.tr.timers.it;

import com.bnpparibas.tr.timers.it.support.TimerRequests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimerCollectionsIntegrationTest extends BaseIntegrationTest {

    @Test
    void collections_persisted_and_returned() throws Exception {
        String body = "{" +
                "\"name\":\"coll\"," +
                "\"zoneId\":\"UTC\"," +
                "\"triggerTime\":\"10:15\"," +
                "\"suspended\":false," +
                "\"countries\":[\"US\",\"CA\"]," +
                "\"excludedCountries\":[\"MX\"]," +
                "\"flowTypes\":[\"FX\"]," +
                "\"clientIds\":[\"C1\"]," +
                "\"productTypes\":[\"CASH\"]" +
                "}";

        String id = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        // Verify via GET
        String getJson = mockMvc.perform(get("/api/timers/" + id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(getJson).contains("\"countries\":[\"US\",\"CA\"]");
        assertThat(getJson).contains("\"excludedCountries\":[\"MX\"]");
        assertThat(getJson).contains("\"flowTypes\":[\"FX\"]");
        assertThat(getJson).contains("\"clientIds\":[\"C1\"]");
        assertThat(getJson).contains("\"productTypes\":[\"CASH\"]");
    }
}


