package com.example.timers.it;

import com.example.timers.it.support.TimerRequests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimerCronUpdateIntegrationTest extends BaseIntegrationTest {

    @Test
    void update_cronExpression_reflectsInQuartzTables() throws Exception {
        String id = mockMvc.perform(post("/api/templates").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.createCron("cron1", "UTC", "0 0 12 * * ?", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        // Ensure initial cron exists
        Integer cronRows = jdbc.queryForObject("select count(*) from QRTZ_CRON_TRIGGERS ct join QRTZ_TRIGGERS t on ct.TRIGGER_NAME=t.TRIGGER_NAME and ct.TRIGGER_GROUP=t.TRIGGER_GROUP where t.TRIGGER_NAME=? and t.TRIGGER_GROUP='instances' and ct.CRON_EXPRESSION='0 0 12 * * ?'", Integer.class, id);
        assertThat(cronRows).isEqualTo(1);

        // Update cron
        mockMvc.perform(put("/api/templates/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.update("cron1", "UTC", null, "0 0 13 * * ?", false)))
                .andExpect(status().isOk());

        // New cron expression reflected
        Integer cronRowsAfter = jdbc.queryForObject("select count(*) from QRTZ_CRON_TRIGGERS ct join QRTZ_TRIGGERS t on ct.TRIGGER_NAME=t.TRIGGER_NAME and ct.TRIGGER_GROUP=t.TRIGGER_GROUP where t.TRIGGER_NAME=? and t.TRIGGER_GROUP='instances' and ct.CRON_EXPRESSION='0 0 13 * * ?'", Integer.class, id);
        assertThat(cronRowsAfter).isEqualTo(1);
    }
}


