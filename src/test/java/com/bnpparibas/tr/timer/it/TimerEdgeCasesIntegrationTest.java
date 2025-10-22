package com.bnpparibas.tr.timer.it;

import com.bnpparibas.tr.timer.it.support.TimerRequests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimerEdgeCasesIntegrationTest extends BaseIntegrationTest {

    @Test
    void update_nonExistent_returns404() throws Exception {
        mockMvc.perform(put("/api/timers/non-existent").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.update("x", "UTC", "10:15", null, false)))
                .andExpect(status().isNotFound());
    }

    @Test
    void idempotent_pause_resume() throws Exception {
        String id = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.create("idem", "UTC", "10:15", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        // pause twice
        mockMvc.perform(put("/api/timers/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.update("idem", "UTC", "10:15", null, true)))
                .andExpect(status().isOk());
        mockMvc.perform(put("/api/timers/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.update("idem", "UTC", "10:15", null, true)))
                .andExpect(status().isOk());
        Integer paused = jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='PAUSED'", Integer.class, id);
        assertThat(paused).isEqualTo(1);

        // resume twice
        mockMvc.perform(put("/api/timers/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.update("idem", "UTC", "10:15", null, false)))
                .andExpect(status().isOk());
        mockMvc.perform(put("/api/timers/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.update("idem", "UTC", "10:15", null, false)))
                .andExpect(status().isOk());
        Integer waiting = jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id);
        assertThat(waiting).isEqualTo(1);
    }

    @Test
    void batch_trigger_creates_executions() throws Exception {
        String id1 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.create("btr1", "UTC", "10:15", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        String id2 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.create("btr2", "UTC", "10:15", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        String batch = "{\"ids\":[\"" + id1 + "\",\"" + id2 + "\"]}";
        mockMvc.perform(post("/api/timers/_trigger").contentType(MediaType.APPLICATION_JSON).content(batch))
                .andExpect(status().isNoContent());
    }
}


