package com.example.timers.it;

import com.example.timers.it.support.TimerRequests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimerValidationIntegrationTest extends BaseIntegrationTest {

    @Test
    void create_invalidZone_returns400() throws Exception {
        String body = TimerRequests.create("bad-zone", "Not/AZone", "10:15", false);
        mockMvc.perform(post("/api/templates").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_invalidCron_returns400() throws Exception {
        String body = TimerRequests.createCron("bad-cron", "UTC", "not-a-cron", false);
        mockMvc.perform(post("/api/templates").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }
}


